package com.wodbot.eventlisteners.lifecycleEventListeners;

import com.wodbot.eventlisteners.EventListener;
import discord4j.core.event.domain.lifecycle.ReconnectEvent;

import static com.wodbot.eventlisteners.Helper.messageCachedChannels;

public class ReconnectEventListener implements EventListener<ReconnectEvent> {

    @Override
    public void accept(ReconnectEvent reconnectEvent) {
        messageCachedChannels(reconnectEvent, "Odzyskałem połączenie");
    }
}
