package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoginStrategy extends BaseStrategy {

    @Autowired
    private UserService userService;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String username = getData()[1];
        String password = getData()[2];

        boolean loggedIn = userService.loginUser(username, password, getDiscordUser());

        if (loggedIn) {
            setLocale(Optional.ofNullable(userService.getLocaleFromCache(getDiscordUser().get())));
        }

        String response = loggedIn ? getMessage("login.success") : getMessage("login.fail");

        sendResponse(response);
    }
}
