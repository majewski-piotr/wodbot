package com.wodbot.eventhandlers;

import discord4j.core.object.entity.Message;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import com.wodbot.eventhandlers.RollCalculators.*;
import org.apache.log4j.Logger;

public enum MessageHandler {

    NUMBER_ONLY ("[0-9]+",new CalculateRoll()),
    NUMBER_AND_NUMBER ("[0-9]+\\s+[0-9]+", new CalculateRollWithDifficulty());

    private final String regex;
    private final Consumer<Message> consumer;
    private static final Logger log = Logger.getLogger(MessageHandler.class);


    MessageHandler(String regex, Consumer<Message> consumer){
        this.regex = regex;
        this.consumer = consumer;
    }

    public boolean matches(Message message){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(message.getContent()).matches();
    }

    public void accept(Message message){
        log.info(this.name() + " handles message: " + message.getContent());
        consumer.accept(message);
    }
}
