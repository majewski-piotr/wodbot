package com.wodbot.eventlisteners.strategies.authenticated;

import com.wodbot.entity.Player;
import com.wodbot.entity.PlayerCharacter;
import com.wodbot.eventlisteners.strategies.EventStrategy;
import com.wodbot.service.PlayerService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import java.util.Arrays;

import static com.wodbot.eventlisteners.Helper.parseFromBrackets;

public class SetCharacteristicStrategy implements EventStrategy<MessageCreateEvent> {

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        PlayerService playerService = PlayerService.getInstance();

        Player player = playerService.getPlayer(message.getAuthor().get().getId());
        PlayerCharacter playerCharacter = player.getCurrentPlayerCharacter();
        String content = message.getContent();
        String field = parseFromBrackets(content);
        String value = content.substring(content.indexOf(field) + field.length()+1).trim();

        switch (field) {
            case "name":
                playerCharacter.setName(value);
                break;
            case "clan":
                playerCharacter.setClan(Arrays.stream(PlayerCharacter.Clan.values())
                        .filter(c -> c.name().equalsIgnoreCase(value)).findFirst().get());
                break;
            case "strength":
                playerCharacter.setStrength(Byte.valueOf(value));
                break;
            case "dexterity":
                playerCharacter.setDexterity(Byte.valueOf(value));
                break;
            case "stamina":
                playerCharacter.setStamina(Byte.valueOf(value));
                default: message.getChannel().block().createMessage("Brak takiej wartości na karcie postaci").block();

        }

    }
}
