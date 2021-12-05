package com.wodbot.eventlisteners.messageEventListeners;

import com.wodbot.authentication.Authenticator;
import com.wodbot.eventlisteners.EventListener;
import com.wodbot.eventlisteners.strategies.*;
import com.wodbot.eventlisteners.strategies.authenticated.*;
import com.wodbot.eventlisteners.strategies.regular.CalculateRollStrategy;
import com.wodbot.eventlisteners.strategies.regular.HelpStrategy;
import com.wodbot.eventlisteners.strategies.regular.LogIn;
import discord4j.core.event.domain.message.MessageCreateEvent;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class CreateMessageEventListener implements EventListener<MessageCreateEvent> {

    private static final Logger log = Logger.getLogger(CreateMessageEventListener.class);

    @Override
    public void accept(MessageCreateEvent event) {

        for (PublicRegexMatcher regexMatcher : PublicRegexMatcher.values()) {
            if (regexMatcher.test(event)) {
                log.info(regexMatcher.name() + " handles message: " + event.getMessage().getContent());
                regexMatcher.getStrategy().accept(event);
            }
        }
        if (Authenticator.getInstance().authenticate(event)) {
            for (AuthenticatedRegexMatcher authenticatedRegexMatcher : AuthenticatedRegexMatcher.values()) {
                {
                    if(authenticatedRegexMatcher.test(event)){
                        log.info(authenticatedRegexMatcher.name() + " handles message: " + event.getMessage().getContent());
                        authenticatedRegexMatcher.getStrategy().accept(event);
                    }
                }
            }
        }
    }

    private enum PublicRegexMatcher implements Predicate<MessageCreateEvent>{
        NUMBER_ONLY("[0-9]+",new CalculateRollStrategy(false,false)),
        NUMBER_AND_NUMBER("[0-9]+\\s+[0-9]+", new CalculateRollStrategy(true,false)),
        NUMBER_AND_NUMBER_PLUS("[0-9]+\\s+[0-9]+\\s\\+",new CalculateRollStrategy(true,true)),
        HELP("\\s*((?i)\\b(help|pomocy?)\\b)\\s*",new HelpStrategy()),
        LOG_IN("logIn",new LogIn());

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
    private enum AuthenticatedRegexMatcher implements Predicate<MessageCreateEvent>{

        OPTIONS("\\s*((?i)\\b(opcje)\\b)\\s*",new LoggedOptionsStrategy()),
        CREATE_CHARACTER("stwórz postać \\[[a-zA-Z\\s]+\\]+", new CreateCharacterStrategy()),
        SHOW_CHARACTER("wyświetl postać \\[[a-zA-Z\\s]+\\]", new ShowCharacterStrategy()),
        SET_CURRENT("ustaw postać \\[[a-zA-Z\\s]+\\]+", new SetCharacterStrategy()),
        SET_FIELD("ustaw \\[[a-zA-Z\\s]+\\] [a-zA-Z0-9]+",new SetCharacteristicStrategy());
        private final String regex;
        private final EventStrategy eventStrategy;

        AuthenticatedRegexMatcher(String regex, EventStrategy eventStrategy){
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
