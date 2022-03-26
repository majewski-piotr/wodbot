package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    private Map<String, EventStrategy> regularMatcher = new HashMap<>();

    public MessageCreateListener() {
        regularMatcher.put("[0-9]+", new RollStrategy());
        regularMatcher.put("[0-9]+\\s+[0-9]+", new RollWithDifficultyStrategy());
        regularMatcher.put("[0-9]+\\s+[0-9]+\\s\\+", new RollWithSpecialization());
        regularMatcher.put("[0-9]+\\s+[0-9]+\\sd", new RollDamage());
        regularMatcher.put("help", new HelpStrategy());
    }

    private static final Logger log = LoggerFactory.getLogger(MessageCreateListener.class);

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        looper(event, regularMatcher);
    }

    private void looper(MessageCreateEvent event, Map<String, EventStrategy> matcher) {
        for (String regex : matcher.keySet()) {
            if (Pattern.compile(regex).matcher(event.getMessage().getContent()).matches()) {
                EventStrategy strategy = matcher.get(regex);
                log.debug("Consuming " + event.getClass().getSimpleName() + " with " + strategy.getClass().getSimpleName());
                strategy.accept(event);
                break;
            }
        }
    }
}
