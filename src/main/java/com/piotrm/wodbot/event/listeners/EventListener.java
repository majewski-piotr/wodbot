package com.piotrm.wodbot.event.listeners;

import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public interface EventListener<T extends Event> extends Consumer<T> {

    Logger LOG = LoggerFactory.getLogger(EventListener.class);

    Class<T> getEventType();

    default Mono<Void> handleError(Throwable error) {
        LOG.error("Unable to process " + getEventType().getSimpleName(), error);
        return Mono.empty();
    }
}