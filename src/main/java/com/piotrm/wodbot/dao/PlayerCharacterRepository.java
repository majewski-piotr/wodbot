package com.piotrm.wodbot.dao;

import com.piotrm.wodbot.model.PlayerCharacter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerCharacterRepository extends MongoRepository<PlayerCharacter,String> {
}
