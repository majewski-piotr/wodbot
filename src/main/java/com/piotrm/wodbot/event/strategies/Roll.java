package com.piotrm.wodbot.event.strategies;

import java.util.Arrays;
import java.util.stream.Collectors;


public class Roll extends MessageCreateEventStrategy {
    protected int dicePool;
    protected int[] rolls;

    @Override
    public void accept(discord4j.core.event.domain.message.MessageCreateEvent event) {
        setUp(event);

        String response = String.format("%s rolls \n %s",getAuthor(),getRollsAsString());

        sendResponse(response);
    }

    @Override
    protected void setUp(discord4j.core.event.domain.message.MessageCreateEvent event) {
        super.setUp(event);
        setDicePool();
        setRolls();
    }

    private void setDicePool() {
        this.dicePool = Integer.parseInt(data[0]);
    }

    private void setRolls() {
        this.rolls = new int[this.dicePool];
        for (int i = 0; i < this.dicePool; i++) {
            int roll = ((int) ((Math.random() * 10) + 1));
            this.rolls[i] = roll;
        }
    }

    protected String getRollsAsString(){
        String rolls = Arrays.stream(this.rolls)
                .mapToObj(i -> String.valueOf(i))
                .map(i -> i.equals("1") ? String.format("**%s**", i) : i)
                .collect(Collectors.joining(" "));
        return rolls;
    }
}
