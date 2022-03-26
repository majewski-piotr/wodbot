package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        Predicate<String> regex = s -> Pattern.compile(s)
                .matcher(event.getMessage().getContent())
                .matches();


        if (regex.test("[0-9]+"))
            new Roll().accept(event);

        else if (regex.test("[0-9]+\\s+[0-9]+"))
            new RollWithDifficulty().accept(event);

        else if (regex.test("[0-9]+\\s+[0-9]+\\s\\+"))
            new RollWithSpecialization().accept(event);

        else if (regex.test("[0-9]+\\s+[0-9]+\\sd"))
            new RollDamage().accept(event);

        else if (regex.test("help"))
            new Help().accept(event);

    }
}
