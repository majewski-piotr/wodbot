package com.wodbot.eventlisteners.strategies;

import discord4j.core.event.domain.Event;

import java.util.function.Consumer;

public interface EventStrategy<T extends Event> extends Consumer<T> {
}
