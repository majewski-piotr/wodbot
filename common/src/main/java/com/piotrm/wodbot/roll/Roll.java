package com.piotrm.wodbot.roll;

import java.util.ArrayList;
import java.util.List;

public class Roll {
    protected final int diceQuantity;
    protected List<Integer> rollResult;
    protected String authorId;

    public Roll(int diceQuantity, String authorId) {
        this.diceQuantity = diceQuantity;
        this.authorId = authorId;
        this.rollResult = new ArrayList<>(diceQuantity);
    }

    public void roll() {
        for (int i = 0; i < diceQuantity; i++) {
            rollResult.add((int) ((Math.random() * 10) + 1));
        }
    }

    protected String getMention() {
        return String.format("<@%s>", authorId);
    }

    protected String getRolls() {
        StringBuilder sb = new StringBuilder();
        for (int i : rollResult) {
            sb.append(" ").append(i);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(getMention()).append(" rolls\n").append(getRolls());
        return sb.toString();
    }
}