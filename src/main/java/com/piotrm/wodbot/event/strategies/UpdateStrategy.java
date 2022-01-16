package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class UpdateStrategy extends BaseStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String characterName = getData()[1];
        String section = getData()[2];
        String fieldName = getData()[3];
        String newValue = getData()[4];
        Long userIdOrDefault = getUserId().orElse(-1L);

        boolean flag = true;
        switch (section) {
            case "attribute":
                playerCharacterService.updateAttribute(userIdOrDefault, characterName, fieldName, Byte.valueOf(newValue));
                break;
            case "ability":
                playerCharacterService.updateAbility(userIdOrDefault, characterName, fieldName, Byte.valueOf(newValue));
                break;
            case "info":
                playerCharacterService.updateInfo(userIdOrDefault, characterName, fieldName, newValue);
                break;
            case "resource":
                playerCharacterService.updateResources(userIdOrDefault, characterName, fieldName, Byte.valueOf(newValue));
                break;
            default:
                flag = false;
        }

        String response = flag ? getMessage("update.success") : getMessage("update.fail");
        sendResponse(response);
    }
}
