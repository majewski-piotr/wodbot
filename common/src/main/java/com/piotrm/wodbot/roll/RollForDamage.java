package com.piotrm.wodbot.roll;

public class RollForDamage extends RollWithDifficulty {

    public RollForDamage(int diceQuantity, String authorId, byte difficulty) {
        super(diceQuantity, authorId, difficulty);
    }

    @Override
    public void roll() {
        for (int i = 0; i < diceQuantity; i++) {
            rollResult.add((int) ((Math.random() * 10) + 1));
        }
        for (int i : rollResult) {
            if (i >= difficulty) {
                passed++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append("damage related roll at difficulty **").append(difficulty).append("**").append(GAME_DICE)
                .append(getRolls()).append("\n")
                .append("**SUCCESSES**: **").append(passed - ones).append("** \n");

        return sb.toString();
    }
}
