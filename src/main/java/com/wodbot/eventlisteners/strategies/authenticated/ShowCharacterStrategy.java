package com.wodbot.eventlisteners.strategies.authenticated;

import com.wodbot.entity.Player;
import com.wodbot.eventlisteners.strategies.EventStrategy;
import com.wodbot.service.PlayerService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

import static com.wodbot.eventlisteners.Helper.parseFromBrackets;


public class ShowCharacterStrategy implements EventStrategy<MessageCreateEvent> {
    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();

        Player player = PlayerService.getInstance().getPlayer(message.getAuthor().get().getId());

        String name = parseFromBrackets(message.getContent());
        String playerCharacterSummary = player.getCharacters().get(name).toString();

        MessageChannel channel = message.getChannel().block();
        channel.createMessage(playerCharacterSummary).block();
        message.delete().block();
    }
}
