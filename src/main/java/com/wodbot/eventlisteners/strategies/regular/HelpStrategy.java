package com.wodbot.eventlisteners.strategies.regular;

import com.wodbot.eventlisteners.strategies.EventStrategy;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import static com.wodbot.eventlisteners.Helper.getAuthor;

public class HelpStrategy implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        StringBuilder help = new StringBuilder();
        help.append("Aby rzucić wpisz tylko liczbę kości, np:\n`3`\n");
        help.append("Aby rzucić ze stopniem trudności wpisz liczbę kości a następnie stopień trudności poprzedzony spacją, np:\n`3 8`\n");
        help.append("Aby rzucić ze stopniem trudności i specjalizacją, dodaj na końcu + poprzedzony spacją, np:\n`3 8 +`\n");
        MessageChannel channel = message.getChannel().block();
        channel.createMessage("Wyświetlam opcje dla " + getAuthor(message) + ":\n" + help.toString()).block();
        message.delete().block();
    }
}
