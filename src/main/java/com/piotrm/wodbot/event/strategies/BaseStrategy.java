package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Component
@Getter
@Setter
public abstract class BaseStrategy implements EventStrategy<MessageCreateEvent>{

    private static final Logger log = LoggerFactory.getLogger(BaseStrategy.class);

    private Message message;
    private String[] data;

    protected void setUp(MessageCreateEvent event) {
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split("\\s+");
    }

    protected void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).onErrorResume(e -> Mono.empty()).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }

    protected String getAuthor() {
        return this.message.getAuthor().get().getMention();
    }

}
