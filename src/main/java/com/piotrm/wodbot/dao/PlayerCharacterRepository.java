package com.piotrm.wodbot.dao;

import com.piotrm.wodbot.model.PlayerCharacter;

import java.util.List;
import java.util.Optional;

public interface PlayerCharacterRepository {
    void save(Long playerId, PlayerCharacter playerCharacter);
    Optional<PlayerCharacter> getPlayerCharacter(Long playerId, String characterName);
    List<PlayerCharacter> getAllPlayerCharacters(Long playerId);
}
