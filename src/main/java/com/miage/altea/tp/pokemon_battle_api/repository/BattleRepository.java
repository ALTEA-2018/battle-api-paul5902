package com.miage.altea.tp.pokemon_battle_api.repository;

import com.miage.altea.tp.pokemon_battle_api.bo.Battle;

import java.util.List;

public interface BattleRepository {

    List<Battle> getBattles();
    Battle getBattle(Integer id);
    Battle create(Battle battle);
}
