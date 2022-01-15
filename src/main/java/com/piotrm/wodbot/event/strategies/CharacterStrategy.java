package com.piotrm.wodbot.event.strategies;


import com.piotrm.wodbot.model.PlayerCharacter;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

import static com.piotrm.wodbot.event.listeners.message.MessageCreateListener.*;

@Component
public class CharacterStrategy extends LoggedInStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = "Coś poszło nie tak";
        if(operation.equals(GET)){
            response = playerCharacterService.getCharacter(userId,characterName).toString();
        } else if(operation.equals(CREATE)){

            response = playerCharacterService.saveNew(userId,characterName)?"Stworzono":"Coś poszło nie tak";
        }
        sendResponse(response);
    }
}
