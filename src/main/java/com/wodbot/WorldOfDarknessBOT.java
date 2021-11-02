package com.wodbot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;

import com.wodbot.eventhandlers.messageEventHandlers.MessageHandler;
import org.apache.log4j.Logger;

public class WorldOfDarknessBOT {

    private static final Logger log = Logger.getLogger(WorldOfDarknessBOT.class);

    public static void main(final String[] args) {
        final DiscordClient client = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        final GatewayDiscordClient gateway = client.login().block();

        gateway.on(MessageCreateEvent.class).subscribe(event -> {
            final Message message = event.getMessage();
            for (MessageHandler handler : MessageHandler.values()) {
                if (handler.matches(message)) {
                    handler.accept(message);
                }
            }
        });
        gateway.onDisconnect().block();
    }
}
