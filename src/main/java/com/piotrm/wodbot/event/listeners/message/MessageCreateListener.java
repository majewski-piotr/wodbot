package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.BotConfiguration;
import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.*;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {
    private Map<String, EventStrategy> matcher = new HashMap<>();

    @Autowired
    public MessageCreateListener(RegisterStrategy registerStrategy, LoginStrategy loginStrategy, StatusStrategy statusStrategy) {
        matcher.put("[0-9]+", new CalculateRollStrategy(false, false));
        matcher.put("[0-9]+\\s+[0-9]+", new CalculateRollStrategy(true, false));
        matcher.put("[0-9]+\\s+[0-9]+\\s\\+", new CalculateRollStrategy(true, true));
        matcher.put("\\s*((?i)\\b(register)\\b)\\s+\\S+\\s+\\S+\\s+\\S+", registerStrategy);
        matcher.put("\\s*((?i)\\b(login)\\b)\\s+\\S+\\s+\\S+", loginStrategy);
        matcher.put("\\s*((?i)\\b(status)\\b)\\s*", statusStrategy);
        matcher.put("\\s*((?i)\\b(help|pomocy?)\\b)\\s*", new HelpStrategy());

    }

    private static final Logger log = LoggerFactory.getLogger(MessageCreateListener.class);

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
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
