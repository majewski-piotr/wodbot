package com.wodbot;

import com.wodbot.cache.DiscordServersCache;
import com.wodbot.dao.DummyDatabase;
import com.wodbot.eventlisteners.lifecycleEventListeners.DisconnectEventListener;
import com.wodbot.eventlisteners.lifecycleEventListeners.ReconnectEventListener;
import com.wodbot.eventlisteners.messageEventListeners.CreateMessageEventListener;
import com.wodbot.service.PlayerService;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.DisconnectEvent;
import discord4j.core.event.domain.lifecycle.ReconnectEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.apache.log4j.Logger;

public class WorldOfDarknessBOT {

    private static final Logger log = Logger.getLogger(WorldOfDarknessBOT.class);

    public static void main(final String[] args) {
        PlayerService.getInstance(DummyDatabase.getInstance());

//        DiscordServersCache.getInstance().getSnowflalkeChannelId().add(Snowflake.of(System.getenv("CHANNEL_ID")));
        final DiscordClient client = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        final GatewayDiscordClient gateway = client.login().block();

//        gateway.on(ConnectEvent.class).subscribe(new ConnectEventListener());
        gateway.on(DisconnectEvent.class).subscribe(new DisconnectEventListener());
        gateway.on(ReconnectEvent.class).subscribe(new ReconnectEventListener());
        gateway.on(MessageCreateEvent.class).subscribe(new CreateMessageEventListener());


        gateway.onDisconnect().block();
    }
}