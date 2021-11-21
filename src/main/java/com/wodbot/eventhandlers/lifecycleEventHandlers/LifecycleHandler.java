package com.wodbot.eventhandlers.lifecycleEventHandlers;

import com.wodbot.eventhandlers.Handler;
import discord4j.core.event.domain.lifecycle.*;
import org.apache.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.wodbot.eventhandlers.lifecycleEventHandlers.GatewayLifecycleConsumers.alertLocalhost;
import static com.wodbot.eventhandlers.lifecycleEventHandlers.GatewayLifecycleConsumers.updatePresence;
import static com.wodbot.eventhandlers.lifecycleEventHandlers.GatewayLifecycleConsumers.messageChannels;

import static com.wodbot.eventhandlers.Handler.isClass;

public enum LifecycleHandler implements Handler<GatewayLifecycleEvent> {

    DISCONNECT(isClass(DisconnectEvent.class),alertLocalhost()),
    CONNECT(isClass(ConnectEvent.class),updatePresence()),
    RECONNECT(isClass(ReconnectEvent.class),messageChannels());

    private final Predicate<GatewayLifecycleEvent> predicate;
    private final Consumer<GatewayLifecycleEvent> consumer;
    private static final Logger log = Logger.getLogger(LifecycleHandler.class);

    LifecycleHandler(Predicate<GatewayLifecycleEvent> predicate, Consumer<GatewayLifecycleEvent> consumer){
        this.predicate = predicate;
        this.consumer = consumer;
    }

    @Override
    public void accept(GatewayLifecycleEvent gatewayLifecycleEvent) {
        log.info(this.name() + " handles event: " + gatewayLifecycleEvent.toString());
        consumer.accept(gatewayLifecycleEvent);
    }

    @Override
    public boolean test(GatewayLifecycleEvent gatewayLifecycleEvent) {
        return predicate.test(gatewayLifecycleEvent);
    }
}
