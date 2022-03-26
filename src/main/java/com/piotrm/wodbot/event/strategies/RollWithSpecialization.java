package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class RollWithSpecialization extends RollWithDifficultyStrategy {

    private int additionalSuccesses;
    private void setAdditionalSuccesses() {
        for (int i : super.getRolls()) {
            if (i == 10) {
                this.additionalSuccesses++;
            }
        }
    }

    @Override
    protected void setUp(MessageCreateEvent event) {
        super.setUp(event);
        setAdditionalSuccesses();
    }

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);
        String response = String.format("%s rolls with specialisation\n%s\npassed\t%d\nones\t%d\nsuccessess **%d**",
                getAuthor(),
                getRollsAsString().replace(" 10 "," **10** "),
                passed,
                ones,
                passed - ones + additionalSuccesses);

        sendResponse(response);
    }
}
