package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CalculateRollStrategy extends BaseStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        boolean specialised = getData().length > 2;
        boolean withDifficulty = getData().length > 1;

        String response = null;
        try {
            List<Integer> rolls = getRolls(Integer.parseInt(getData()[0]));
            if (withDifficulty) {
                int difficulty = Integer.parseInt(getData()[1]);
                int successes = getSuccesses(rolls, difficulty, specialised);

                response = getAuthor(getMessage()) + " " + "throws" + " " +
                        (specialised ? "With speciality" : ":") + "\n" + rollsToString(rolls) +
                        String.format("\n" + "with difficulty" + ": **%s**\n" +
                                "successes" + ": **%s**", difficulty, successes);
            } else {
                response = getAuthor(getMessage()) + " " + "rolls" + "\n" + rollsToString(rolls);
            }
        } catch (NullPointerException e) {
        } finally {
            sendResponse(response);
        }
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


    public static String getAuthor(Message message) {
        return message.getAuthor().get().getMention();
    }
}
