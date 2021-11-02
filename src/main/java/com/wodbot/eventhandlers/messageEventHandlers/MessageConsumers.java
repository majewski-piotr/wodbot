package com.wodbot.eventhandlers.messageEventHandlers;

import java.util.List;
import java.util.function.Consumer;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import org.apache.log4j.Logger;
import reactor.core.publisher.Mono;

import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.getNumbers;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.getRolls;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.rollsToString;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.getSuccesses;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.successesToString;
import static com.wodbot.eventhandlers.messageEventHandlers.MessageConsumerHelper.getAuthor;

final class MessageConsumers {

    private MessageConsumers(){}

    private static final Logger log = Logger.getLogger(MessageConsumers.class);

    static Consumer<Message> calculateRoll(boolean withDifficulty, boolean specialised) {
        return message -> {
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
                            }).subscribe();

                } else {
                    channel.createMessage(getAuthor(message) + " rzuca:\n" + rollsToString(rolls)).block();
                }
            } catch (NullPointerException e) {
                log.error(e.getMessage(), e);
            }
            message.delete().subscribe();
        };
    }

    static Consumer<Message> showHelp() {
        return message -> {
            StringBuilder help = new StringBuilder();
            help.append("Aby rzucić wpisz tylko liczbę kości, np:\n`3`\n");
            help.append("Aby rzucić ze stopniem trudności wpisz liczbę kości a następnie stopień trudności poprzedzony spacją, np:\n`3 8`\n");
            help.append("Aby rzucić ze stopniem trudności i specjalizacją, dodaj na końcu + poprzedzony spacją, np:\n`3 8 +`\n");
            MessageChannel channel = message.getChannel().block();
            channel.createMessage("Wyświetlam opcje dla " + getAuthor(message) + ":\n" + help.toString()).subscribe();
            message.delete().subscribe();
        };
    }
}
