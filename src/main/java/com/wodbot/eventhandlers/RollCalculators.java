package com.wodbot.eventhandlers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;

class RollCalculators {

    public static class CalculateRoll implements Consumer<Message>{
        int diceNumber;
        List<Integer> numbers;
        MessageChannel channel;
        List<Integer> rolls = new ArrayList<>();

        @Override
        public void accept(Message message) {
            setChannel(message);
            calculateFromIndexZero(message);
            channel.createMessage(getRollsAsString(rolls)).block();
        }

        void setChannel(Message message){
            channel = message.getChannel().block();
        }

        void calculateFromIndexZero(Message message){
            numbers = Arrays.stream(message.getContent().split(" ")).map(Integer::parseInt).collect(Collectors.toList());
            diceNumber = numbers.get(0);
            for(int i = 0; i<diceNumber;i++){
                int roll = ((int)((Math.random()*10)+1));
                rolls.add(roll);
            }
        }

        String getRollsAsString(List<Integer> rolls){
            return String.join(", ",rolls.stream()
                    .map(i -> i==10||i==1?" **"+i+"** ":String.valueOf(i))
                    .collect(Collectors.toList()));
        }
    }

    public static class CalculateRollWithDifficulty extends CalculateRoll{

        int difficulty = 6;
        int successes = 0;

        @Override
        public void accept( Message message) {
            setChannel(message);
            calculateFromIndexZero(message);
            difficulty = numbers.get(1);
            for(int roll : rolls){
                if(roll>=difficulty){
                    ++successes;
                }else if(roll == 1){
                    --successes;
                }
            }
            channel.createMessage(getRollsAsString(rolls) + ", successess: " + successes
            ).block();
        }
    }
}
