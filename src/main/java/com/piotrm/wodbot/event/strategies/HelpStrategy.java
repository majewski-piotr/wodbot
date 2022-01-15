package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class HelpStrategy extends MessageCreateStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        StringBuilder help = new StringBuilder();
        help.append(getMessage("help.banner.allUsers")).append(getMessage("help.roll.simple"))
                .append(getMessage("help.roll.difficulty")).append(getMessage("help.roll.specialized"))
                .append(getMessage("help.register")).append(getMessage("help.login"))
                .append(getMessage("help.banner.loggedUsers")).append(getMessage("help.create"))
                .append(getMessage("help.get")).append(getMessage("help.update"))
                .append(getMessage("help.update.sections"));
        MessageChannel channel = message.getChannel().block();
        channel.createMessage("Wyświetlam opcje:\n" + help).block();
        message.delete().block();
    }
}
