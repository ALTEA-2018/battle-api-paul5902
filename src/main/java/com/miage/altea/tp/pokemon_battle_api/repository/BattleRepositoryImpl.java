package com.miage.altea.tp.pokemon_battle_api.repository;

import com.miage.altea.tp.pokemon_battle_api.bo.Battle;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BattleRepositoryImpl implements BattleRepository {

    private Integer i;

    private Map<Integer, Battle> battles;

    public BattleRepositoryImpl() {
        this.i = 0;
        this.battles = new HashMap<>();
    }

    public List<Battle> getBattles() {
        return new ArrayList<>(this.battles.values());
    }
    
    public Battle getBattle(Integer id) {
        return this.battles.get(id);
    }

    @Override
    public Battle create(Battle battle) {
        battle.setUuid(i);
        battles.put(i, battle);
        return this.getBattle(i+1);
    }
}
