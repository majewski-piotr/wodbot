package com.piotrm.wodbot.event;

import discord4j.core.object.entity.Message;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Helper {

    public static List<Integer> getNumbers(Message message) {
        Pattern number = Pattern.compile("[0-9]+");
        return Arrays.stream(message.getContent().split(" ")).filter(number.asPredicate()).map(Integer::parseInt).collect(Collectors.toList());
    }

    public static List<Integer> getRolls(int diceNumber) {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < diceNumber; i++) {
            int roll = ((int) ((Math.random() * 10) + 1));
            rolls.add(roll);
        }
        return rolls;
    }

    public static String rollsToString(List<Integer> rolls) {
        return String.join(", ", rolls.stream()
                .map(i -> i == 10 || i == 1 ? " **" + i + "** " : String.valueOf(i))
                .collect(Collectors.toList()));
    }

    public static int getSuccesses(List<Integer> rolls, int difficulty, boolean specialised) {
        return rolls.stream().reduce(0, (integer, integer2) -> {
            if (integer2 >= difficulty) {
                if (specialised && integer2 == 10) {
                    return integer + 2;
                } else {
                    return ++integer;
                }
            } else if (integer2 == 1) {
                return --integer;
            } else return integer;
        });
    }

    public static String successesToString(int difficulty, int successes) {
        return String.format("\nStopień trudności: **%s**\nSukcesy: **%s**", difficulty, successes);
    }

    public static String getAuthor(Message message) {
        return message.getAuthor().get().getMention();
    }
}
