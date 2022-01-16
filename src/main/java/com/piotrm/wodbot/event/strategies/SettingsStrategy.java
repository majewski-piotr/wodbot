package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class SettingsStrategy extends BaseStrategy {


    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);
        String response;
        String option = getData()[1];
        String value = getData()[2];
        switch (option) {
            case "language":
                response = userService.changeLocale(getDiscordUser().get(), value) ?
                        getMessage("settings.success") : getMessage("settings.fail");
                break;
            default: response = getMessage("general.fail");
        }
        sendResponse(response);
    }
}
