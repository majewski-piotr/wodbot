package com.piotrm.wodbot.dao;

import com.piotrm.wodbot.model.PlayerCharacter;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PlayerCharacterInMemStore implements PlayerCharacterRepository{
    private Map<Long,Map<String, PlayerCharacter>> playerCharacters = new HashMap<>();


    @Override
    public Optional<PlayerCharacter> getPlayerCharacter(Long playerId, String characterName) {
        return Optional.ofNullable(playerCharacters.get(playerId).get(characterName));
    }

    @Override
    public List<PlayerCharacter> getAllPlayerCharacters(Long playerId) {
        return new ArrayList<>(playerCharacters.get(playerId).values());
    }

    @Override
    public void save(Long playerId, PlayerCharacter playerCharacter) {
        Map<String, PlayerCharacter> currentPlayerCharacters = playerCharacters.get(playerId);
        if(currentPlayerCharacters == null){
            currentPlayerCharacters = new HashMap<>();
        }
        currentPlayerCharacters.put(playerCharacter.getName(),playerCharacter);
    }
}
