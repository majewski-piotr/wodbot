package com.wodbot.authentication;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.User;

import java.util.HashSet;
import java.util.Set;

public final class Authenticator {

    private static volatile Authenticator instance;

    private Set<Snowflake> loggedUsers = new HashSet<>();

    public boolean authenticate(MessageCreateEvent event) {
        return loggedUsers.contains(event.getMessage().getAuthor().get().getId());
    }

    public void logIn(User user) {
        loggedUsers.add(user.getId());
    }

    public static Authenticator getInstance() {
        Authenticator result = instance;
        if (instance != null) {
            return result;
        }
        synchronized (Authenticator.class) {
            if (instance == null) {
                instance = new Authenticator();
            }
            return instance;
        }
    }
}
