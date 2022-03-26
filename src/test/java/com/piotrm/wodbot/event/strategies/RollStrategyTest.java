package com.piotrm.wodbot.event.strategies;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RollStrategyTest {

    @Test
    void getRollsAsString() {

        int [] test = new int[]{1,2,3};

        String rolls = Arrays.stream(test)
                .mapToObj(i -> String.valueOf(i))
                .map(i -> i.equals("1") || i.equals("10") ? String.format("**%s**", i) : i)
                .collect(Collectors.joining(" "));

        assertEquals("**1** 2 3",rolls);
    }
}