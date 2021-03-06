package com.miage.altea.tp.pokemon_battle_api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This battle is already finished.")
public class BattleFinishedException extends Exception {
    public BattleFinishedException(String message){
        super(message);
    }
}
