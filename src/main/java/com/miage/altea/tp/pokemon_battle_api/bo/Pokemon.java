package com.miage.altea.tp.pokemon_battle_api.bo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import lombok.Data;

public class Pokemon {

    private int id;

    private int pokemonType;

    private PokemonType pokemonTypeObject;

    private int level;

    private Stats currentStats;

    public void setCurrentStats(Stats baseStats, int level) {
        this.currentStats = new Stats();
        this.currentStats.setAttack(5 + (baseStats.getAttack() * level / 50));
        this.currentStats.setSpeed(5 + (baseStats.getSpeed() * level / 50));
        this.currentStats.setDefense(5 + (baseStats.getDefense() * level / 50));
        this.currentStats.setHp(10 + level + (baseStats.getHp() * level / 50));

    }


    public Pokemon() {
    }

    public Pokemon(int pokemonType, int level) {
        this.pokemonType = pokemonType;
        this.level = level;
    }


    @JsonProperty("alive")
    public boolean alive(){
        if(currentStats.getHp()>=0) {
            return true;
        }
        return false;
    }

    @JsonProperty("ko")
    public boolean ko(){
        return !alive();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPokemonType() {
        return pokemonType;
    }

    public void setPokemonType(int pokemonType) {
        this.pokemonType = pokemonType;
    }

    public PokemonType getPokemonTypeObject() {
        return pokemonTypeObject;
    }

    public void setPokemonTypeObject(PokemonType pokemonTypeObject) {
        this.pokemonTypeObject = pokemonTypeObject;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Stats getCurrentStats() {
        return currentStats;
    }

    public void setCurrentStats(Stats currentStats) {
        this.currentStats = currentStats;
    }
}