package com.piotrm.wodbot.event.strategies;


import com.piotrm.wodbot.service.PlayerCharacterService;
import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.reaction.ReactionEmoji;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Optional;

@Component
@Getter
@Setter
public abstract class BaseStrategy implements EventStrategy<MessageCreateEvent>{

    private static final Logger log = LoggerFactory.getLogger(BaseStrategy.class);

    @Autowired
    protected UserService userService;
    @Autowired
    protected PlayerCharacterService playerCharacterService;
    @Autowired
    private ResourceBundleMessageSource messageSource;

    private Message message;
    private String[] data;
    private Optional<User> discordUser;
    private Optional<Locale> locale;
    private Optional<Long> userId;

    public void setUp(MessageCreateEvent event) {
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split("\\s+");
        this.discordUser = event.getMessage().getAuthor();
        this.locale = Optional.ofNullable(userService.getLocaleFromCache(discordUser.get()));
        this.userId = Optional.ofNullable(userService.getUserIdFromDiscordUser(discordUser.get()));
    }

    public void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).onErrorResume(e -> Mono.empty()).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }
    public void sendResponse(String response,String unicodeEmoji) {
        this.message.getChannel().block().createMessage(response).flatMap(
                msg -> msg.addReaction(ReactionEmoji.unicode(unicodeEmoji))
        ).onErrorResume(e -> Mono.empty()).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }

    protected String getMessage(String property){
        return messageSource.getMessage(property,null,locale.orElse(new Locale.Builder()
                .setLanguage("en").setRegion("EN").build()));
    }



}
