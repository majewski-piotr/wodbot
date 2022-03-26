package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class RollWithDifficultyStrategy extends RollStrategy {
    protected int passed;
    protected int ones;
    protected int difficulty;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = String.format("%s rolls \n%s\ndifficulty\t%d\npassed\t%d\nones\t%d\nsuccessess **%d**",
                super.getAuthor(),
                super.getRollsAsString(),
                difficulty,
                passed,
                ones,
                passed - ones);

        sendResponse(response);
    }

    protected void setSuccesses() {
        ones = 0;
        passed = 0;
        for (int roll : super.getRolls()) {
            if (roll == 1)
                ones++;
            else if (roll >= difficulty)
                passed++;
        }
    }

    private void setDifficulty() {
        this.difficulty = Integer.parseInt(super.getData()[1]);
    }

    @Override
    protected void setUp(MessageCreateEvent event) {
        super.setUp(event);
        setDifficulty();
        setSuccesses();
    }
}
