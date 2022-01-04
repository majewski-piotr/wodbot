package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.BotConfiguration;
import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.CalculateRollStrategy;
import com.piotrm.wodbot.event.strategies.EventStrategy;
import com.piotrm.wodbot.event.strategies.HelpStrategy;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;

@Service
public class MessageCreateListener implements EventListener<MessageCreateEvent> {

    private static final Logger log = LoggerFactory.getLogger( MessageCreateListener.class );

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        for (PublicRegexMatcher regexMatcher : PublicRegexMatcher.values()) {
            if (regexMatcher.test(event)) {
                log.info("Accepting "+ event.getClass().getSimpleName());
                regexMatcher.getStrategy().accept(event);
            }
        }
    }


    private enum PublicRegexMatcher implements Predicate<MessageCreateEvent> {
        NUMBER_ONLY("[0-9]+",new CalculateRollStrategy(false,false)),
        NUMBER_AND_NUMBER("[0-9]+\\s+[0-9]+", new CalculateRollStrategy(true,false)),
        NUMBER_AND_NUMBER_PLUS("[0-9]+\\s+[0-9]+\\s\\+",new CalculateRollStrategy(true,true)),
        HELP("\\s*((?i)\\b(help|pomocy?)\\b)\\s*",new HelpStrategy());


        private final String regex;
        private final EventStrategy eventStrategy;

        PublicRegexMatcher(String regex, EventStrategy eventStrategy){
            this.regex = regex;
            this.eventStrategy = eventStrategy;
        }

        @Override
        public boolean test(MessageCreateEvent messageCreateEvent) {
            Pattern pattern = Pattern.compile(regex);
            return pattern.matcher(messageCreateEvent.getMessage().getContent()).matches();
        }

        EventStrategy getStrategy(){
            return eventStrategy;
        }
    }
}