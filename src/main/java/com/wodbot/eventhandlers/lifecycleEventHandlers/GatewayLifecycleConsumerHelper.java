package com.wodbot.eventhandlers.lifecycleEventHandlers;

import com.wodbot.cache.DiscordServersCache;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.lifecycle.GatewayLifecycleEvent;

class GatewayLifecycleConsumerHelper {

    static void messageCachedChannels(GatewayLifecycleEvent gatewayLifecycleEvent, String message) {
        if (!DiscordServersCache.getInstance().getSnowflalkeChannelId().isEmpty()) {
            for (Snowflake id : DiscordServersCache.getInstance().getSnowflalkeChannelId()) {
                gatewayLifecycleEvent.getClient().getChannelById(id).block().getRestChannel().createMessage(message).block();
            }
        }
    }
}
