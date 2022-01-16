package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.PlayerCharacterService;
import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.Optional;

public abstract class LoggedInStrategy extends MessageCreateStrategy {
    @Autowired
    protected PlayerCharacterService playerCharacterService;
    @Autowired
    RedisTemplate<Long, Long> redisTemplate;
    @Autowired
    UserService userService;
    protected String[] data;
    protected Long userId;
    private Message message;
    protected String field2;
    protected String field1;
    protected Optional<User> discordUser;

    private static final Logger log = LoggerFactory.getLogger(LoggedInStrategy.class);

    public void setUp(MessageCreateEvent event) {
        this.discordUser = event.getMessage().getAuthor();
        this.userId = redisTemplate.opsForValue().get(discordUser.get().getId().asLong());
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split("\\s+");
        field2 = data[1];
        field1 = data[0];
    }

    public void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).block();
        this.message.delete().onErrorResume(e -> Mono.empty()).block();
    }

    @Override
    public Locale getLocale(){
        return userService.getLocaleFromCache(discordUser.get());
    }

    @Override
    String getMessage(String property){
        return getMessageSource().getMessage(property,null,getLocale());
    }
}
