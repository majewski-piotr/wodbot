package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class SettingsStrategy extends LoggedInStrategy {


    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);
        String response;
        switch (field2) {
            case "language":
                response = userService.changeLocale(discordUser.get(), data[2]) ?
                        getMessage("settings.success") : getMessage("settings.fail");
                break;
            default: response = getMessage("general.fail");
        }
        sendResponse(response);
    }
}
