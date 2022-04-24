package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageCreateEventStrategy implements EventStrategy<MessageCreateEvent> {

    protected Message message;
    protected String[] data;

    protected void setUp(MessageCreateEvent event) {
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split("\\s+");
    }

    protected void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).onErrorResume(e -> Mono.empty()).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }

    protected String getAuthor() {
        return this.message.getAuthor().get().getMention();
    }

}
