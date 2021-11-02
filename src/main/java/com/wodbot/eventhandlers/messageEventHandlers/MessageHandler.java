package com.wodbot.eventhandlers.messageEventHandlers;

import discord4j.core.object.entity.Message;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumers.calculateRoll;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumers.showHelp;

public enum MessageHandler implements Consumer<Message> {

    NUMBER_ONLY("[0-9]+", calculateRoll(false, false)),
    NUMBER_AND_NUMBER("[0-9]+\\s+[0-9]+", calculateRoll(true, false)),
    NUMBER_AND_NUMBER_PLUS("[0-9]+\\s+[0-9]+\\s\\+", calculateRoll(true, true)),
    HELP("\\s*((?i)\\b(help|pomocy?)\\b)\\s*", showHelp());

    private final String regex;
    private final Consumer<Message> consumer;
    private static final Logger log = Logger.getLogger(MessageHandler.class);


    MessageHandler(String regex, Consumer<Message> consumer) {
        this.regex = regex;
        this.consumer = consumer;
    }

    public boolean matches(Message message) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(message.getContent()).matches();
    }

    public void accept(Message message) {
        log.info(this.name() + " handles message: " + message.getContent());
        consumer.accept(message);
    }
}
