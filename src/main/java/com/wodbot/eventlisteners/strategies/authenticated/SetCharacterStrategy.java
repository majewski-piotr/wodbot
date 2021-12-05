package com.wodbot.eventlisteners.strategies.authenticated;

import com.wodbot.entity.Player;

import com.wodbot.eventlisteners.strategies.EventStrategy;
import com.wodbot.service.PlayerService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import static com.wodbot.eventlisteners.Helper.parseFromBrackets;

public class SetCharacterStrategy implements EventStrategy<MessageCreateEvent> {

    public void accept(MessageCreateEvent event) {
        PlayerService playerService = PlayerService.getInstance();
        Message message = event.getMessage();

        Player player = playerService.getPlayer(message.getAuthor().get().getId());

        String name = parseFromBrackets(message.getContent());
        player.setCurrentPlayerCharacter(player.getCharacters().get(name));

        playerService.updatePlayer(player);
    }
}

