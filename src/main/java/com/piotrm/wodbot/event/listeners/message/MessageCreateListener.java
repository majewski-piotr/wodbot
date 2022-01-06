package com.piotrm.wodbot.event.listeners.message;

import com.piotrm.wodbot.BotConfiguration;
import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.event.strategies.CalculateRollStrategy;
import com.piotrm.wodbot.event.strategies.EventStrategy;
import com.piotrm.wodbot.event.strategies.HelpStrategy;
import com.piotrm.wodbot.event.strategies.RegisterStrategy;
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
    public MessageCreateListener(RegisterStrategy registerStrategy){
        matcher.put("[0-9]+",new CalculateRollStrategy(false,false));
        matcher.put("[0-9]+\\s+[0-9]+",new CalculateRollStrategy(true,false));
        matcher.put("[0-9]+\\s+[0-9]+\\s\\+",new CalculateRollStrategy(true,true));
        matcher.put("\\s*((?i)\\b(register)\\b)\\s*\\S+\\s*\\S+\\s*\\S+",registerStrategy);
        matcher.put("\\s*((?i)\\b(help|pomocy?)\\b)\\s*",new HelpStrategy());

    }
    private static final Logger log = LoggerFactory.getLogger( MessageCreateListener.class );

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        for(String regex : matcher.keySet()){
            if(Pattern.compile(regex).matcher(event.getMessage().getContent()).matches()){
                matcher.get(regex).accept(event);
                break;
            }
        }
    }

//    private enum PublicRegexMatcher implements Predicate<MessageCreateEvent> {
//        NUMBER_ONLY("[0-9]+",new CalculateRollStrategy(false,false)),
//        NUMBER_AND_NUMBER("[0-9]+\\s+[0-9]+", new CalculateRollStrategy(true,false)),
//        NUMBER_AND_NUMBER_PLUS("[0-9]+\\s+[0-9]+\\s\\+",new CalculateRollStrategy(true,true)),
//        REGISTER("\\s*((?i)\\b(register)\\b)\\s*\\S+\\s*\\S+\\s*\\S+",appContext.getBean(RegisterStrategy.class)),
//        HELP("\\s*((?i)\\b(help|pomocy?)\\b)\\s*",new HelpStrategy());
//
//
//        private final String regex;
//        private final EventStrategy eventStrategy;
//
//        PublicRegexMatcher(String regex, EventStrategy eventStrategy){
//            this.regex = regex;
//            this.eventStrategy = eventStrategy;
//        }
//
//        @Override
//        public boolean test(MessageCreateEvent messageCreateEvent) {
//            Pattern pattern = Pattern.compile(regex);
//            return pattern.matcher(messageCreateEvent.getMessage().getContent()).matches();
//        }
//
//        EventStrategy getStrategy(){
//            return eventStrategy;
//        }
//    }
}