package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginStrategy extends AuthStrategy {

    @Autowired
    private UserService userService;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = userService.loginUser(data[1], data[2], discordUser) ? "Zalogowano użytkonika" : "Coś poszło nie tak";

        sendResponse(response);
    }
}
