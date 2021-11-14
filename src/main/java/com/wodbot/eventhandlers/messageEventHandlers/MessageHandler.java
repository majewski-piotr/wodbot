package com.wodbot.eventhandlers.messageEventHandlers;

import com.wodbot.eventhandlers.Handler;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.wodbot.cache.DiscordServersCache;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumers.calculateRoll;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumers.showHelp;

public enum MessageHandler implements Handler<MessageCreateEvent> {

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

    public boolean test(MessageCreateEvent event) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(event.getMessage().getContent()).matches();
    }

    public void accept(MessageCreateEvent event) {
        DiscordServersCache.getInstance().getSnowflalkeChannelId().add(event.getMessage().getChannelId());
        log.info(this.name() + " handles message: " +
                event.getMessage().getContent() +
                ", and adding to chache " +
                event.getMessage().getChannelId());
        consumer.accept(event.getMessage());
    }
}
