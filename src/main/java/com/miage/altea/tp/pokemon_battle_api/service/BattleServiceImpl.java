package com.miage.altea.tp.pokemon_battle_api.service;

import com.miage.altea.tp.pokemon_battle_api.bo.Battle;
import com.miage.altea.tp.pokemon_battle_api.bo.Pokemon;
import com.miage.altea.tp.pokemon_battle_api.bo.Trainer;
import com.miage.altea.tp.pokemon_battle_api.exceptions.BattleFinishedException;
import com.miage.altea.tp.pokemon_battle_api.exceptions.NotYourTurnException;
import com.miage.altea.tp.pokemon_battle_api.repository.BattleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BattleServiceImpl implements BattleService {

    private String trainerServiceUrl;
    private RestTemplate trainerApiRestTemplate;


    @Autowired
    @Qualifier("trainerApiRestTemplate")
    public void setRestTemplate(RestTemplate trainerApiRestTemplate) {
        this.trainerApiRestTemplate = trainerApiRestTemplate;
    }

    @Value("${trainer.service.url}")
    public void setTrainerServiceUrl(String trainerServiceUrl) {
        this.trainerServiceUrl = trainerServiceUrl;
    }


    @Autowired
    private BattleRepository battleRepository;


    @Autowired
    private PokemonTypeService pokemonTypeService;


    @Override
    public List<Battle> getAllBattles() {

        return battleRepository.getBattles();
    }

    @Override
    public Battle getBattle(Integer id) {

        return battleRepository.getBattle(id);
    }

    @Override
    public Battle createBattle(String name1, String name2) {
        Trainer trainer1 = getTrainer(name1);
        Trainer trainer2 = getTrainer(name2);
        List<Pokemon> team1 = trainer1.getTeam();
        for(Pokemon p1 : team1){
            p1.setCurrentStats(p1.getPokemonTypeObject().getStats().toBuilder().build(), p1.getLevel());
        }

        List<Pokemon> team2 = trainer2.getTeam();
        for(Pokemon p2 : team1){
            p2.setCurrentStats(p2.getPokemonTypeObject().getStats().toBuilder().build(), p2.getLevel());
        }

        Integer currentTrainerStart=2;
        Boolean startWithTrainer1 = false;
        if(team1.get(0).getCurrentStats().getSpeed() >= team2.get(0).getCurrentStats().getSpeed()) {
            startWithTrainer1= true;
            currentTrainerStart=1;
        }
        trainer1.setNextTurn(startWithTrainer1);
        trainer2.setNextTurn(!startWithTrainer1);

        Battle battle = new Battle(trainer1,trainer2,currentTrainerStart);
        return battleRepository.create(battle);

    }

    @Override
    public Battle attack(Integer id, String trainerName) throws NotYourTurnException, BattleFinishedException {
        Battle battle = getBattle(id);
        Boolean isTurn = false;
        int currentTrainer = battle.getCurrentTrainer();
        if (currentTrainer == 1 && trainerName.equals(battle.getTrainer1().getName())) {
            isTurn = true;
        }
        if (currentTrainer == 2 && trainerName.equals(battle.getTrainer2().getName())) {
            isTurn = true;
        }
        if (!isTurn) {
            throw new NotYourTurnException("It's not your turn.");
        }

        Pokemon attacker = getAttacker(battle);
        Pokemon defender = getDefender(battle);
        if (attacker == null || defender == null) {
            throw new BattleFinishedException("The battle is finished.");
        }

        fight(attacker, defender);

        if(battle.getCurrentTrainer() == 1) {
            battle.setCurrentTrainer(2);
        }
        else {
            battle.setCurrentTrainer(1);
        }
        battle.getTrainer1().setNextTurn(!battle.getTrainer1().getNextTurn());
        battle.getTrainer2().setNextTurn(!battle.getTrainer2().getNextTurn());
        return battle;
    }

    private void fight(Pokemon attacker, Pokemon defender) {
        Integer attack = ((2 * attacker.getLevel() / 5) + (2 * attacker.getCurrentStats().getAttack() / defender.getCurrentStats().getDefense())) + 2;
        defender.getCurrentStats().setHp(defender.getCurrentStats().getHp() - attack);
    }

    private Pokemon getAttacker(Battle battle) {
        Trainer trainer = (battle.getCurrentTrainer() == 1) ? battle.getTrainer1() : battle.getTrainer2();
        return trainer.getTeam().stream().filter(pokemon -> pokemon.getCurrentStats().getHp() > 0).findFirst().orElse(null);
    }

    private Pokemon getDefender(Battle battle) {
        Trainer trainer = (battle.getCurrentTrainer() == 1) ? battle.getTrainer2() : battle.getTrainer1();
        return trainer.getTeam().stream().filter(pokemon -> pokemon.getCurrentStats().getHp() > 0).findFirst().orElse(null);
    }

    private Trainer getTrainer(String name) {
        Trainer trainer = this.trainerApiRestTemplate.getForObject(this.trainerServiceUrl + "/trainers/" + name, Trainer.class);
        List<Pokemon> team = trainer.getTeam();
        for(Pokemon p : team){
            p.setCurrentStats(p.getPokemonTypeObject().getStats(), p.getLevel());
        }
        return trainer;
    }
}
