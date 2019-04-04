package com.miage.altea.tp.pokemon_battle_api.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Battle {

    private Integer uuid;

    private Trainer trainer1;

    private Trainer trainer2;

    private int currentTrainer;

    public Battle(Trainer trainer1, Trainer trainer2, int currentTrainer) {
        this.trainer1 = trainer1;
        this.trainer2 = trainer2;
        this.currentTrainer = currentTrainer;
    }

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Trainer getTrainer1() {
        return trainer1;
    }

    public void setTrainer1(Trainer trainer1) {
        this.trainer1 = trainer1;
    }

    public Trainer getTrainer2() {
        return trainer2;
    }

    public void setTrainer2(Trainer trainer2) {
        this.trainer2 = trainer2;
    }

    public int getCurrentTrainer() {
        return currentTrainer;
    }

    public void setCurrentTrainer(int currentTrainer) {
        this.currentTrainer = currentTrainer;
    }
}
