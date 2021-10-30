package com.wodbot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import com.wodbot.eventhandlers.MessageHandler;

public class WorldOfDarknessBOT {

    public static void main(final String[] args) {
        final DiscordClient client = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            for(MessageHandler handler : MessageHandler.values()){
                if(handler.matches(message)){
                    handler.accept(message);
                }
            }
        });
            gateway.onDisconnect().block();
    }
}
