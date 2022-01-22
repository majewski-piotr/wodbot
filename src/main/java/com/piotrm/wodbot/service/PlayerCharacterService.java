package com.piotrm.wodbot.service;

import com.piotrm.wodbot.dao.PlayerCharacterRepository;
import com.piotrm.wodbot.model.PlayerCharacter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import reactor.util.annotation.Nullable;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
public class PlayerCharacterService {

    @Autowired
    PlayerCharacterRepository playerCharacterRepository;

    @Autowired
    ResourceBundleMessageSource messageSource;

    public boolean saveNew(Long userId, String characterName) {
        PlayerCharacter playerCharacter = new PlayerCharacter();
        playerCharacter.setPlayerId(userId);
        playerCharacter.setName(characterName);
        playerCharacterRepository.save(playerCharacter);

        return true;
    }

    public String toString(PlayerCharacter playerCharacter, Locale locale) {

        String name = messageSource.getMessage("characterSheet.name", null, locale);
        String infos = messageSource.getMessage("characterSheet.infos", null, locale);
        String attributes = messageSource.getMessage("characterSheet.attributes", null, locale);
        String abilities = messageSource.getMessage("characterSheet.abilities", null, locale);
        String backgrounds = messageSource.getMessage("characterSheet.backgrounds", null, locale);

        StringBuilder stringBuilder = new StringBuilder(300);
        stringBuilder.append(name + ": " + playerCharacter.getName() + "\n");
        stringBuilder.append("**" + infos + "**\n");
        for (Map.Entry<String, String> entry : playerCharacter.getInfo().entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**" + attributes + "**\n");
        for (Map.Entry<String, Byte> entry : playerCharacter.getAttributes().entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**" + abilities + "**\n");
        for (Map.Entry<String, Byte> entry : playerCharacter.getAbilities().entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**" + backgrounds + "**\n");
        for (Map.Entry<String, Byte> entry : playerCharacter.getResources().entrySet()) {
            appender(stringBuilder, entry);
        }
        return stringBuilder.toString();
    }

    private void appender(StringBuilder sb, Map.Entry entry) {
        sb.append("\t").append(entry.getKey()).append(" `").append(entry.getValue()).append("`\n");
    }


    public Optional<PlayerCharacter> getCharacter(Long userId, String charactername){
        PlayerCharacter matcherObject = new PlayerCharacter();
        matcherObject.setPlayerId(userId);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withMatcher("playerId", exact());
        Example<PlayerCharacter> example = Example.of(matcherObject, matcher);
        List<PlayerCharacter> playerCharacters = playerCharacterRepository.findAll(example);
        for (PlayerCharacter p : playerCharacters) {
            if (p.getName().equals(charactername)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
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
        Optional<PlayerCharacter> playerCharacter = getCharacter(userId, characterName);
        if (!playerCharacter.isPresent()) {
            return false;
        }
        playerCharacter.get().getInfo().put(fieldName, newValue);
        playerCharacterRepository.save(playerCharacter.get());
        return true;
    }

    private boolean modifyCharacter(Long userId, String characterName, String fieldName, Byte newValue,
                                    Function<PlayerCharacter, Map<String, Byte>> transformer) {
        Optional<PlayerCharacter> playerCharacter = getCharacter(userId, characterName);
        if (!playerCharacter.isPresent()) {
            return false;
        }
        transformer.apply(playerCharacter.get()).put(fieldName, newValue);
        playerCharacterRepository.save(playerCharacter.get());
        return true;
    }
}
