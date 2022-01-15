package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

@Getter
@Setter
public abstract class MessageCreateStrategy implements EventStrategy<MessageCreateEvent> {
    @Autowired
    private ResourceBundleMessageSource messageSource;
    private Locale locale = new Locale.Builder().setLanguage("pl").setRegion("PL").build();

    String getMessage(String property){
        return messageSource.getMessage(property,null,locale);
    }
}
