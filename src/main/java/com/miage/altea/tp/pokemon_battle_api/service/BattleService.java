package com.miage.altea.tp.pokemon_battle_api.service;

import com.miage.altea.tp.pokemon_battle_api.bo.Battle;
import com.miage.altea.tp.pokemon_battle_api.exceptions.BattleFinishedException;
import com.miage.altea.tp.pokemon_battle_api.exceptions.NotYourTurnException;

import java.util.List;

public interface BattleService {

    Battle getBattle(Integer id);

    List<Battle> getAllBattles();

    Battle createBattle(String trainer1, String trainer2);

    Battle attack(Integer id, String trainerName) throws NotYourTurnException, BattleFinishedException;
}
