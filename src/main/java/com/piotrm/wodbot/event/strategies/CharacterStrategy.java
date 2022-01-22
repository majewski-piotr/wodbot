package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.model.PlayerCharacter;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.piotrm.wodbot.event.listeners.message.MessageCreateListener.*;

@Component
public class CharacterStrategy extends BaseStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String operation = getData()[0];
        String characterName = getData()[1];

        String response = getMessage("general.fail");
        if (operation.equals(GET)) {
            Optional<PlayerCharacter> playerCharacter = playerCharacterService.getCharacter(getUserId().get(), characterName);
            if (playerCharacter.isPresent()) {
                response = playerCharacterService.toString(playerCharacter.get(), getLocale().get());
            }
        } else if (operation.equals(CREATE)) {
            response = playerCharacterService.saveNew(getUserId().get(), characterName) ? getMessage("create.success") : getMessage("create.fail");
        }
        sendResponse(response);
    }
}
