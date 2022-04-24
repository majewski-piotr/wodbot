package com.piotrm.wodbot.roll;

public class RollWithSpecialisation extends RollWithDifficulty {

    public RollWithSpecialisation(int diceQuantity, String authorId, byte difficulty) {
        super(diceQuantity, authorId, difficulty);
    }

    @Override
    public void roll() {
        super.roll();
        for (int i : rollResult) {
            if (i == 10) {
                passed++;
            }
        }
    }

    private String getRollResultMarkTens() {
        return getRollResultMarkOnes().replace("10", "**10**");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(getMention()).append(" rolls with specialisation, difficulty **").append(difficulty).append("** -> ")
                .append(getRollResultMarkTens()).append("\n")
                .append("**SUCCESSES**: **").append(passed - ones).append("** \n");

        return sb.toString();
    }
}
