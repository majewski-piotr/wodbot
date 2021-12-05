package com.wodbot.eventlisteners.lifecycleEventListeners;

import com.wodbot.eventlisteners.EventListener;
import discord4j.core.event.domain.lifecycle.DisconnectEvent;

import java.awt.*;

public class DisconnectEventListener implements EventListener<DisconnectEvent> {

    @Override
    public void accept(DisconnectEvent disconnectEvent) {
        Toolkit.getDefaultToolkit().beep();
    }
}
