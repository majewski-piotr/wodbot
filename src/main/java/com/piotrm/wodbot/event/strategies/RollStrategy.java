package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class RollStrategy extends BaseStrategy {
    private int dicePool;
    private int[] rolls;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = String.format("%s rolls \n %s",getAuthor(),getRollsAsString());

        sendResponse(response);
    }

    @Override
    protected void setUp(MessageCreateEvent event) {
        super.setUp(event);
        setDicePool();
        setRolls();
    }

    private void setDicePool() {
        this.dicePool = Integer.parseInt(getData()[0]);
    }

    private void setRolls() {
        this.rolls = new int[this.dicePool];
        for (int i = 0; i < this.dicePool; i++) {
            int roll = ((int) ((Math.random() * 10) + 1));
            this.rolls[i] = roll;
        }
    }

    protected int [] getRolls(){
        return rolls;
    }

    protected String getRollsAsString(){
        String rolls = Arrays.stream(this.rolls)
                .mapToObj(i -> String.valueOf(i))
                .map(i -> i.equals("1") ? String.format("**%s**", i) : i)
                .collect(Collectors.joining(" "));
        return rolls;
    }
}
