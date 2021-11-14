package com.wodbot;

import com.wodbot.cache.DiscordServersCache;
import com.wodbot.eventhandlers.lifecycleEventHandlers.LifecycleHandler;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.GatewayLifecycleEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;

import com.wodbot.eventhandlers.messageEventHandlers.MessageHandler;
import org.apache.log4j.Logger;

import static com.wodbot.eventhandlers.HandlerHelper.testAndAccept;

public class WorldOfDarknessBOT {

    private static final Logger log = Logger.getLogger(WorldOfDarknessBOT.class);

    public static void main(final String[] args) {
        DiscordServersCache.getInstance().getSnowflalkeChannelId().add(Snowflake.of(System.getenv("CHANNEL_ID")));
        final DiscordClient client = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        final GatewayDiscordClient gateway = client.login().block();
        gateway.on(GatewayLifecycleEvent.class).subscribe(event -> testAndAccept(event, LifecycleHandler.values()));
        gateway.on(MessageCreateEvent.class).subscribe(event -> testAndAccept(event, MessageHandler.values()));
        gateway.onDisconnect().block();
    }
}