package com.miage.altea.tp.pokemon_battle_api.bo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class Trainer {

    private String name; 

    private List<Pokemon> team;


    public Trainer(String name) {
        this.name = name;
    }

    private String password;

    private Boolean nextTurn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pokemon> getTeam() {
        return team;
    }

    public void setTeam(List<Pokemon> team) {
        this.team = team;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getNextTurn() {
        return nextTurn;
    }

    public void setNextTurn(Boolean nextTurn) {
        this.nextTurn = nextTurn;
    }


}