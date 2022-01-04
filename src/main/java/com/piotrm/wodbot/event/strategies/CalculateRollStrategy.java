package com.piotrm.wodbot.event.strategies;


import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

import java.util.List;

import static com.piotrm.wodbot.event.Helper.*;


public class CalculateRollStrategy implements EventStrategy<MessageCreateEvent> {

    private boolean withDifficulty;
    private boolean specialised;



    public CalculateRollStrategy(boolean withDifficulty, boolean specialised) {
        this.withDifficulty = withDifficulty;
        this.specialised = specialised;
    }

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = message.getChannel().block();
        List<Integer> numbers = getNumbers(message);
        List<Integer> rolls = getRolls(numbers.get(0));
        try {
            if (withDifficulty) {
                int difficulty = numbers.get(1);
                int successes = getSuccesses(rolls, difficulty, specialised);
                channel.createMessage(getAuthor(message) + " rzuca" + (specialised ? " ze specjalizacją:" : ":") +
                        "\n" + rollsToString(rolls) + successesToString(difficulty, successes))
                        .flatMap(msg -> {
                            if (successes < 0) {
                                return msg.addReaction(ReactionEmoji.unicode("\u2620"));
                            }
                            return Mono.just(msg);
                        }).block();

            } else {
                channel.createMessage(getAuthor(message) + " rzuca:\n" + rollsToString(rolls)).block();
            }
        } catch (NullPointerException e) {
        }
        message.delete().block();
    }
}
