package com.wodbot.eventlisteners.strategies.authenticated;

import com.wodbot.eventlisteners.strategies.EventStrategy;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

public class LoggedOptionsStrategy implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = message.getChannel().block();

        StringBuilder sb = new StringBuilder();
        sb.append("`stwórz postać [Pan Paweł]` stworzy nową postać z podaną nazwą\n");
        sb.append("`wyświetl postać [Pan Paweł]` wyświetli wszystkie informacje o Panie Paweł\n");
        sb.append("`ustaw [Siła] 3` ustawi siłę 3 dla aktualnie ustawionej postaci\n");
        channel.createMessage("Witam serdecznie zalogowanego użytkownika. Poniżej przedstawiam dostępne opcje:\n" + sb).block();
    }
}
