package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.Optional;

public abstract class AuthStrategy extends  MessageCreateStrategy{
    private Message message;
    protected String[] data;
    protected Optional<User> discordUser;

    private static final Logger log = LoggerFactory.getLogger(AuthStrategy.class);

    public void setUp(MessageCreateEvent event) {
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split("\\s+");
        this.discordUser = event.getMessage().getAuthor();
    }

    public void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }
}
