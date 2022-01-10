package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.PlayerCharacterRepository;
import com.piotrm.wodbot.model.PlayerCharacter;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class PlayerCharacterService {

    @Autowired
    PlayerCharacterRepository playerCharacterRepository;

    public boolean saveNew(Long userId, String characterName){
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setName(characterName);
        playerCharacterRepository.save(userId,playerCharacter);
        return true;
    }

    public PlayerCharacter getCharacter(Long userId, String charactername){
        return playerCharacterRepository.getPlayerCharacter(userId,charactername).orElseThrow(
                () -> new IllegalArgumentException("No such character for this player"));
    }

    public boolean updateAttribute(Long userId, String characterName, String fieldName, Byte newValue){
        return modifyCharacter(userId,characterName,fieldName,newValue,PlayerCharacter::getAttributes);
    }
    public boolean updateAbility(Long userId, String characterName, String fieldName, Byte newValue){
        return modifyCharacter(userId,characterName,fieldName,newValue,PlayerCharacter::getAbilities);
    }
    public boolean updateResources(Long userId, String characterName, String fieldName, Byte newValue){
        return modifyCharacter(userId,characterName,fieldName,newValue,PlayerCharacter::getResources);
    }
    public boolean updateInfo(Long userId, String characterName, String fieldName, String newValue){
        Optional<PlayerCharacter> playerCharacter = playerCharacterRepository.getPlayerCharacter(userId,characterName);
        if(!playerCharacter.isPresent()){
            return false;
        }
        playerCharacter.get().getInfo().put(fieldName,newValue);
        playerCharacterRepository.save(userId,playerCharacter.get());
        return true;
    }

    private boolean modifyCharacter(Long userId, String characterName, String fieldName, Byte newValue,
                                    Function<PlayerCharacter,Map<String,Byte>> transformer){
        Optional<PlayerCharacter> playerCharacter = playerCharacterRepository.getPlayerCharacter(userId,characterName);
        if(!playerCharacter.isPresent()){
            return false;
        }
        transformer.apply(playerCharacter.get()).put(fieldName,newValue);
        playerCharacterRepository.save(userId,playerCharacter.get());
        return true;
    }
}
