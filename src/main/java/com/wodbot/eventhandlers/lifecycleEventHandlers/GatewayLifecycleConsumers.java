package com.wodbot.eventhandlers.lifecycleEventHandlers;

import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.lifecycle.GatewayLifecycleEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.core.object.presence.Status;
import org.apache.log4j.Logger;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import com.wodbot.cache.DiscordServersCache;

public class GatewayLifecycleConsumers {

    private GatewayLifecycleConsumers(){}

    private static final Logger log = Logger.getLogger(GatewayLifecycleConsumers.class);

    static Consumer<GatewayLifecycleEvent> alertLocalhost(){
        return gatewayLifecycleEvent -> {
            Toolkit.getDefaultToolkit().beep();
        };
    }

    static Consumer<GatewayLifecycleEvent> updatePresence(){
        return gatewayLifecycleEvent -> {
            gatewayLifecycleEvent.getClient()
                    .updatePresence(
                            ClientPresence.of(
                                    Status.ONLINE,
                                    ClientActivity.playing("od godziny "+LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                                    )
                            )
                    .block();
            if(!DiscordServersCache.getInstance().getSnowflalkeChannelId().isEmpty()){
                for(Snowflake id : DiscordServersCache.getInstance().getSnowflalkeChannelId()){
                    gatewayLifecycleEvent.getClient().getChannelById(id).block().getRestChannel().createMessage("Jestem online").block();
                }
            }
        };
    }
}
