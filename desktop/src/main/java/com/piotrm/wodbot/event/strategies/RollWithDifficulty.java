package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

public class RollWithDifficulty extends Roll {
    protected int passed;
    protected int ones;
    protected int difficulty;
    protected int successess;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = String.format("%s rolls \n%s\ndifficulty\t%d\npassed\t%d\nones\t%d\nsuccessess **%d**",
                super.getAuthor(),
                super.getRollsAsString(),
                difficulty,
                passed,
                ones,
                successess);

        sendResponse(response);
    }

    protected void setSuccesses() {
        for (int roll : rolls) {
            if (roll == 1)
                ones++;
            else if (roll >= difficulty)
                passed++;
        }
        successess = passed - ones;
    }

    private void setDifficulty() {
        this.difficulty = Integer.parseInt(super.data[1]);
    }

    @Override
    protected void setUp(MessageCreateEvent event) {
        super.setUp(event);
        setDifficulty();
        setSuccesses();
    }

    @Override
    protected void sendResponse(String response) {
        //LEO
        //<@392688473833734164>

        if (successess < 0) {
            message.getChannel().block()
                    .createMessage(response)
                    .flatMap(msg -> {
                        if (getAuthor().equals("<@392688473833734164>")) {
                            return msg.addReaction(ReactionEmoji.unicode("\u2764"));
                        } else {
                            return msg.addReaction(ReactionEmoji.unicode("\u2620"));
                        }
                    })
                    .onErrorResume(e -> Mono.empty())
                    .block();
            message.delete().onErrorResume(e -> Mono.empty()).block();
        } else {
            super.sendResponse(response);
        }
    }
}
