package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class RegisterStrategy implements EventStrategy<MessageCreateEvent> {
    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = message.getChannel().block();
    }
}
