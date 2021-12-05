package com.wodbot.entity;

import discord4j.core.object.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player{

    private  User user;

    private PlayerCharacter currentPlayerCharacter;

    private Map<String,PlayerCharacter> characters = new HashMap<>();

    public Player(User user) {
        this.user = user;
    }

    public PlayerCharacter getCurrentPlayerCharacter() {
        return currentPlayerCharacter;
    }

    public void setCurrentPlayerCharacter(PlayerCharacter currentPlayerCharacter) {
        this.currentPlayerCharacter = currentPlayerCharacter;
    }

    public User getUser(){
        return this.user;
    }

    public Map<String,PlayerCharacter> getCharacters() {
        return characters;
    }
}
