package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import static com.piotrm.wodbot.event.listeners.message.MessageCreateListener.*;

@Component
public class CharacterStrategy extends LoggedInStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = getMessage("general.fail");
        if (operation.equals(GET)) {
            response = playerCharacterService.toString(playerCharacterService.getCharacter(userId, characterName),getLocale());
        } else if (operation.equals(CREATE)) {

            response = playerCharacterService.saveNew(userId, characterName) ? getMessage("create.success") : getMessage("create.fail");
        }
        sendResponse(response);
    }
}
