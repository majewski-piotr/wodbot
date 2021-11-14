package com.wodbot.eventhandlers;

import discord4j.core.event.domain.Event;

import java.util.function.Predicate;

public class HandlerHelper {

    public static <T extends Event> void testAndAccept(T processable, Handler<T>[] handlers) {
        for (Handler handler : handlers) {
            if (handler.test(processable)) {
                handler.accept(processable);
            }
        }
    }

    public static Predicate isClass(Class eventClass){
       return event -> event.getClass().equals(eventClass);
    }
}
