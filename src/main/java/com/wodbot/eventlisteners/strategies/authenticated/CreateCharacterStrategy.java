package com.wodbot.eventlisteners.strategies.authenticated;

import com.wodbot.entity.Player;
import com.wodbot.entity.PlayerCharacter;
import com.wodbot.eventlisteners.strategies.EventStrategy;
import com.wodbot.service.PlayerService;
import discord4j.core.event.domain.message.MessageCreateEvent;

import static com.wodbot.eventlisteners.Helper.parseFromBrackets;

public class CreateCharacterStrategy implements EventStrategy<MessageCreateEvent> {
    @Override
    public void accept(MessageCreateEvent messageCreateEvent) {
        PlayerService playerService = PlayerService.getInstance();
        Player player = playerService.getPlayer(messageCreateEvent.getMessage().getAuthor().get().getId());

        PlayerCharacter playerCharacter = new PlayerCharacter();

        String characterName = parseFromBrackets(messageCreateEvent.getMessage().getContent());
        playerCharacter.setName(characterName);

        player.setCurrentPlayerCharacter(playerCharacter);
        player.getCharacters().put(characterName,playerCharacter);
        playerService.updatePlayer(player);
    }
}
