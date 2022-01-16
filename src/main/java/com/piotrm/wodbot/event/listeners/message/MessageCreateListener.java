package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.*;
import com.piotrm.wodbot.service.UserService;
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

    @Autowired
    UserService userService;

    public static final String REGISTER = "register";
    public static final String LOGIN = "login";
    public static final String STATUS = "status";
    public static final String HELP = "help";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
    public static final String GET = "get";
    public static final String SETTINGS = "settings";

    private Map<String, EventStrategy> regularMatcher = new HashMap<>();
    private Map<String, EventStrategy> loggedInMatcher = new HashMap<>();


    @Autowired
    public MessageCreateListener(RegisterStrategy registerStrategy, LoginStrategy loginStrategy, StatusStrategy statusStrategy,
                                 CharacterStrategy characterStrategy, UpdateStrategy updateStrategy, HelpStrategy helpStrategy,
                                 SettingsStrategy settingsStrategy) {
        regularMatcher.put("[0-9]+", new CalculateRollStrategy(false, false));
        regularMatcher.put("[0-9]+\\s+[0-9]+", new CalculateRollStrategy(true, false));
        regularMatcher.put("[0-9]+\\s+[0-9]+\\s\\+", new CalculateRollStrategy(true, true));
        regularMatcher.put("\\s*((?i)\\b(" + REGISTER + ")\\b)\\s+\\S+\\s+\\S+\\s+\\S+\\s*", registerStrategy);
        regularMatcher.put("\\s*((?i)\\b(" + LOGIN + ")\\b)\\s+\\S+\\s+\\S+\\s*", loginStrategy);
        regularMatcher.put("\\s*((?i)\\b(" + STATUS + ")\\b)\\s*", statusStrategy);
        regularMatcher.put("\\s*((?i)\\b(" + HELP + "|pomocy?)\\b)\\s*", helpStrategy);

        loggedInMatcher.put("\\s*((?i)\\b(" + CREATE + "|" + GET + ")\\b)\\s+\\S+\\s*", characterStrategy);
        loggedInMatcher.put("\\s*((?i)\\b(" + UPDATE + ")\\b)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*", updateStrategy);
        loggedInMatcher.put("\\s*((?i)\\b(" + SETTINGS + ")\\b)\\s+\\S+\\s+\\S+\\s*", settingsStrategy);


    }

    private static final Logger log = LoggerFactory.getLogger(MessageCreateListener.class);

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        looper(event, regularMatcher);
        if (event.getMessage().getAuthor().isPresent() &&
                userService.isLogged(event.getMessage().getAuthor().get())) {
            looper(event, loggedInMatcher);
        }

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
