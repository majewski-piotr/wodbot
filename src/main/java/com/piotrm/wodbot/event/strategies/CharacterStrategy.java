package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.model.PlayerCharacter;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.piotrm.wodbot.event.listeners.message.MessageCreateListener.*;

@Component
public class CharacterStrategy extends LoggedInStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = getMessage("general.fail");
        if (field1.equals(GET)) {
            Optional<PlayerCharacter> playerCharacter = playerCharacterService.getCharacter(userId, field2);
            if (playerCharacter.isPresent()) {
                response = playerCharacterService.toString(playerCharacter.get(), getLocale());
            }
        } else if (field1.equals(CREATE)) {
            response = playerCharacterService.saveNew(userId, field2) ? getMessage("create.success") : getMessage("create.fail");
        }
        sendResponse(response);
    }
}
