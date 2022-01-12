package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.PlayerCharacterRepository;
import com.piotrm.wodbot.model.PlayerCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
public class PlayerCharacterService {

    @Autowired
    PlayerCharacterRepository playerCharacterRepository;

    public boolean saveNew(Long userId, String characterName) {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setPlayerId(userId);
        playerCharacter.setName(characterName);
        playerCharacterRepository.save(playerCharacter);
        return true;
    }

    public PlayerCharacter getCharacter(Long userId, String charactername) {
        PlayerCharacter matcherObject = new PlayerCharacter();
        matcherObject.setPlayerId(userId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withMatcher("playerId", exact());
        Example<PlayerCharacter> example = Example.of(matcherObject, matcher);
        List<PlayerCharacter> playerCharacters = playerCharacterRepository.findAll(example);
        for (PlayerCharacter p : playerCharacters) {
            if (p.getName().equals(charactername)) {
                return p;
            }
        }
        throw new IllegalArgumentException("No such character for this player");
    }

    public boolean updateAttribute(Long userId, String characterName, String fieldName, Byte newValue) {
        return modifyCharacter(userId, characterName, fieldName, newValue, PlayerCharacter::getAttributes);
    }

    public boolean updateAbility(Long userId, String characterName, String fieldName, Byte newValue) {
        return modifyCharacter(userId, characterName, fieldName, newValue, PlayerCharacter::getAbilities);
    }

    public boolean updateResources(Long userId, String characterName, String fieldName, Byte newValue) {
        return modifyCharacter(userId, characterName, fieldName, newValue, PlayerCharacter::getResources);
    }

    public boolean updateInfo(Long userId, String characterName, String fieldName, String newValue) {
        PlayerCharacter playerCharacter = getCharacter(userId, characterName);
        if (playerCharacter == null) {
            return false;
        }
        playerCharacter.getInfo().put(fieldName, newValue);
        playerCharacterRepository.save(playerCharacter);
        return true;
    }

    private boolean modifyCharacter(Long userId, String characterName, String fieldName, Byte newValue,
                                    Function<PlayerCharacter, Map<String, Byte>> transformer) {
        PlayerCharacter playerCharacter = getCharacter(userId, characterName);
        if (playerCharacter == null) {
            return false;
        }
        transformer.apply(playerCharacter).put(fieldName, newValue);
        playerCharacterRepository.save(playerCharacter);
        return true;
    }
}
