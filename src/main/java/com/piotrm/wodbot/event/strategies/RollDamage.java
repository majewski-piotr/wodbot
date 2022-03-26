package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RollDamage extends RollWithDifficultyStrategy{

    @Override
    protected void setSuccesses() {
        passed = 0;
        for (int roll : super.getRolls()) {
            if (roll >= difficulty)
                passed++;
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
                passed);

        sendResponse(response);
    }
}
