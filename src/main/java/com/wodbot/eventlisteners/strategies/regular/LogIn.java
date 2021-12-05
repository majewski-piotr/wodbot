package com.wodbot.eventlisteners.strategies.regular;

import com.wodbot.authentication.Authenticator;
import com.wodbot.entity.Player;
import com.wodbot.eventlisteners.strategies.EventStrategy;
import com.wodbot.service.PlayerService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;

public class LogIn implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent messageCreateEvent) {
        User user = messageCreateEvent.getMessage().getAuthor().get();
        PlayerService.getInstance().logIn(user);
    }
}
