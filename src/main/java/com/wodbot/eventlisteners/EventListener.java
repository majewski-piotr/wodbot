package com.wodbot.eventlisteners;

import discord4j.core.event.domain.Event;
import discord4j.core.event.domain.lifecycle.ConnectEvent;
import discord4j.core.event.domain.lifecycle.DisconnectEvent;
import discord4j.core.event.domain.lifecycle.ReconnectEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface EventListener<T extends Event> extends Consumer<T> {

    String DISCONNECT = DisconnectEvent.class.getCanonicalName();
    String CONNECT = ConnectEvent.class.getCanonicalName();
    String RECONNECT = ReconnectEvent.class.getCanonicalName();
    String MESSAGE_CREATE = MessageCreateEvent.class.getCanonicalName();
}
