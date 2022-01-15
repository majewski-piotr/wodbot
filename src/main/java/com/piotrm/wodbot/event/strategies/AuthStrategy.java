package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;

import java.util.Optional;

public abstract class AuthStrategy extends  MessageCreateStrategy{
    private Message message;
    protected String[] data;
    protected Optional<User> discordUser;

    public void setUp(MessageCreateEvent event) {
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split(" ");
        this.discordUser = event.getMessage().getAuthor();
    }

    public void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).block();
        //TODO: HANDLE INSUFFICIENT PERMOSSION EXCEPTIONS
        this.message.delete().block();
    }
}
