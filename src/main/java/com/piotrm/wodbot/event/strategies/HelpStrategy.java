package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class HelpStrategy implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        StringBuilder help = new StringBuilder();
        help.append("Aby rzucić wpisz tylko liczbę kości, np:\n`3`\n");
        help.append("Aby rzucić ze stopniem trudności wpisz liczbę kości a następnie stopień trudności poprzedzony spacją, np:\n`3 8`\n");
        help.append("Aby rzucić ze stopniem trudności i specjalizacją, dodaj na końcu + poprzedzony spacją, np:\n`3 8 +`\n");
        help.append("Aby się zarejestrować wpisz register ,email, imię i hasło, np: \n`register brian@domain.com noobslayer secretp4ssw0rd`\n");
        MessageChannel channel = message.getChannel().block();
        channel.createMessage("Wyświetlam opcje:\n" + help).block();
        message.delete().block();
    }
}
