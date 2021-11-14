package com.wodbot.eventhandlers;

import discord4j.core.event.domain.Event;

import java.util.function.Consumer;
import java.util.function.Predicate;

public interface Handler<T extends Event> extends Consumer<T>, Predicate<T> {

    static <B extends Event> void testAndAccept(B processable, Handler<B>[] handlers) {
        for (Handler handler : handlers) {
            if (handler.test(processable)) {
                handler.accept(processable);
            }
        }
    }

    static Predicate isClass(Class eventClass) {
        return event -> event.getClass().equals(eventClass);
    }
}
