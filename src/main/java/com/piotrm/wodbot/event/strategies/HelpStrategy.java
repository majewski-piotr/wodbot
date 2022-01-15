package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class HelpStrategy implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        StringBuilder help = new StringBuilder();
        help.append("==== DLA WSZYSTKICH UŻYTKOWNIKÓW ====\n");
        help.append("Aby rzucić wpisz tylko liczbę kości, np:\n\t`3`\n");
        help.append("Aby rzucić ze stopniem trudności wpisz liczbę kości a następnie stopień trudności poprzedzony spacją, np:\n\t`3 8`\n");
        help.append("Aby rzucić ze stopniem trudności i specjalizacją, dodaj na końcu + poprzedzony spacją, np:\n\t`3 8 +`\n");
        help.append("Aby się zarejestrować wpisz register ,email, imię i hasło, np: \n\t`register brian@domain.com noobslayer secretp4ssw0rd`\n");
        help.append("Aby się zalogować wpisz login ,nick i hasło, np: \n\t`login noobslayer secretp4ssw0rd`\n");
        help.append("==== DLA ZALOGOWANYCH UŻYTKOWNIKÓW ====\n");
        help.append("Aby stworzyć postać wpisz create i imię postaci, np: = \n\t`create Leo`\n");
        help.append("Aby wyświetlić postać wpisz get i imię postaci, np: = \n\t`get Leo`\n");
        help.append("Aby zaaktualizować postać wpisz get i imię postaci, sekcję na karcie, nazwę i wartość, np: = \n\t`update Leo attributes strength 5`\n");
        help.append("Dostępne sekcje to : `attribute`, `resource`, `info`, `ability`\n");
        MessageChannel channel = message.getChannel().block();
        channel.createMessage("Wyświetlam opcje:\n" + help).block();
        message.delete().block();
    }
}
