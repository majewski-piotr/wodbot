package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class RollWithSpecialization extends RollWithDifficulty {

    private void setAdditionalSuccesses() {
        for (int i : super.rolls) {
            if (i == 10) {
                successess++;
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
        String response = String.format("%s rolls with specialisation\n%s\ndifficulty\t%d\npassed\t%d\nones\t%d\nsuccessess **%d**",
                getAuthor(),
                getRollsAsString().replace(" 10 "," **10** "),
                difficulty,
                passed,
                ones,
                successess);

        sendResponse(response);
    }
}
