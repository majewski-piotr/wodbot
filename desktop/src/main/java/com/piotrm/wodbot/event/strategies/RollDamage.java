package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

public class RollDamage extends RollWithDifficulty {

    @Override
    protected void setSuccesses() {
        for (int roll : rolls) {
            if (roll >= difficulty)
                successess++;
        }
    }

    @Override
    protected String getRollsAsString(){
       return super.getRollsAsString().replace(" **1** "," 1 ");
    }

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = String.format("%s rolls damage related roll \n%s\ndifficulty %s\nsuccessess **%d**",
                super.getAuthor(),
                getRollsAsString(),
                difficulty,
                successess);

        sendResponse(response);
    }
}
