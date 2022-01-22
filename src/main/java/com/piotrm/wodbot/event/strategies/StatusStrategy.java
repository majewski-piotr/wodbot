package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatusStrategy extends BaseStrategy {

    @Autowired
    private UserService userService;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = userService.isLogged(getDiscordUser().get()) ? getMessage("logged.yes") : getMessage("logged.no");

        sendResponse(response);
    }
}
