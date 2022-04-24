package com.piotrm.wodbot.roll;

import java.util.ArrayList;
import java.util.List;

public class BasicRoll implements Roll {
    private final int diceQuantity;
    private List<Integer> rollResult;
    private String author;

    public BasicRoll(int diceQuantity, String author) {
        this.diceQuantity = diceQuantity;
        this.author = author;
        this.rollResult = new ArrayList<>(diceQuantity);
    }

    @Override
    public void roll() {
        for (int i = 0; i < diceQuantity; i++) {
            rollResult.add((int) ((Math.random() * 10) + 1));
        }
    }

    public String getRollResultMarkOnes() {
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

    public List<Integer> getRollResult() {
        return rollResult;
    }

    public void setRollResult(List<Integer> rollResult) {
        this.rollResult = rollResult;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(50);
        sb.append(author).append(" rolls\n").append(getRollResultMarkOnes());
        return sb.toString();
    }
}
