package com.wodbot.eventhandlers;

import discord4j.core.event.domain.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Handler<T extends Event> extends Consumer<T>, Predicate<T> {}
