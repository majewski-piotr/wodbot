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
                playerCharacterService.updateAttribute(userId, field2, fieldName, Byte.valueOf(newValue));
                break;
            case "ability":
                playerCharacterService.updateAbility(userId, field2, fieldName, Byte.valueOf(newValue));
                break;
            case "info":
                playerCharacterService.updateInfo(userId, field2, fieldName, newValue);
                break;
            case "resource":
                playerCharacterService.updateResources(userId, field2, fieldName, Byte.valueOf(newValue));
                break;
            default:
                flag = false;
        }

        String response = flag ? getMessage("update.success") : getMessage("update.fail");
        sendResponse(response);
    }
}
