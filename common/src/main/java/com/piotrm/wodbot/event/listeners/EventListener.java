package com.piotrm.wodbot.event.listeners;

import discord4j.core.event.domain.Event;
import java.util.function.Consumer;

public interface EventListener<T extends Event> extends Consumer<T> {
    Class<T> getEventType();
}