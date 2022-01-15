package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategy extends LoggedInStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String section = data[2];
        String fieldName = data[3];
        String newValue = data[4];

        boolean flag = true;
        switch (section) {
            case "attribute":
                playerCharacterService.updateAttribute(userId, characterName, fieldName, Byte.valueOf(newValue));
                break;
            case "ability":
                playerCharacterService.updateAbility(userId, characterName, fieldName, Byte.valueOf(newValue));
                break;
            case "info":
                playerCharacterService.updateInfo(userId, characterName, fieldName, newValue);
                break;
            case "resource":
                playerCharacterService.updateResources(userId, characterName, fieldName, Byte.valueOf(newValue));
                break;
            default:
                flag = false;
        }

        String response = flag ? "Zaaktualizowano" : "Coś poszło nie tak";
        sendResponse(response);
    }
}
