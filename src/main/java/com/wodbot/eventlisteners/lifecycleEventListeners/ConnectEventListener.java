package com.wodbot.eventlisteners.lifecycleEventListeners;

import com.wodbot.eventlisteners.EventListener;
import discord4j.core.event.domain.lifecycle.ConnectEvent;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.core.object.presence.Status;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.wodbot.eventlisteners.Helper.messageCachedChannels;

public class ConnectEventListener implements EventListener<ConnectEvent> {

    @Override
    public void accept(ConnectEvent connectEvent) {
        connectEvent.getClient()
                .updatePresence(
                        ClientPresence.of(
                                Status.ONLINE,
                                ClientActivity.playing("od godziny " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")))
                        )
                )
                .block();
        messageCachedChannels(connectEvent, "Połączyłem się.");
    }
}
