package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.PlayerCharacterService;
import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

public abstract class LoggedInStrategy implements EventStrategy<MessageCreateEvent> {
    @Autowired
    protected PlayerCharacterService playerCharacterService;
    @Autowired
    RedisTemplate<Long, Long> redisTemplate;
    protected String[] data;
    protected Long userId;
    private Message message;
    protected String characterName;
    protected String operation;
    protected Optional<User> discordUser;

    public void setUp(MessageCreateEvent event) {
        this.discordUser = event.getMessage().getAuthor();
        this.userId = redisTemplate.opsForValue().get(discordUser.get().getId().asLong());
        this.message = event.getMessage();
        this.data = event.getMessage().getContent().split(" ");
        characterName = data[1];
        operation = data[0];
    }

    public void sendResponse(String response) {
        this.message.getChannel().block().createMessage(response).block();
        //TODO: HANDLE INSUFFICIENT PERMOSSION EXCEPTIONS
        this.message.delete().block();
    }
}
