package com.piotrm.wodbot.roll;

public class RollWithDifficulty extends Roll {
    protected byte difficulty;
    protected byte passed;
    protected byte ones;

    public RollWithDifficulty(int diceQuantity, String authorId, byte difficulty) {
        super(diceQuantity, authorId);
        this.difficulty = difficulty;
    }

    @Override
    public void roll() {
        super.roll();
        for (int i : rollResult) {
            if (i >= difficulty) {
                passed++;
            } else if (i == 1) {
                ones++;
            }
        }
    }

    protected String getRollResultMarkOnes() {
        StringBuilder sb = new StringBuilder();
        for (int i : rollResult) {
            if (i == 1) {
                sb.append(" **").append(i).append("**");
            } else {
                sb.append(" ").append(i);
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append("roll at difficulty **").append(difficulty).append("**").append(GAME_DICE)
                .append(getRollResultMarkOnes()).append("\n")
                .append("**SUCCESSES**: **").append(passed - ones).append("** \n");

        return sb.toString();
    }
}
