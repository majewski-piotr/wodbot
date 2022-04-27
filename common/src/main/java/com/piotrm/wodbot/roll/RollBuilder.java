package com.piotrm.wodbot.roll;

public class RollBuilder {
    private boolean isSpecialised;
    private boolean isDamageRoll;
    private boolean isWithDifficulty;
    private byte difficulty;
    private byte quantity;
    private final String authorId;

    public RollBuilder(String authorId) {
        this.authorId = authorId;
    }

    public RollBuilder withQuantity(byte quantity) {
        this.quantity = quantity;
        return this;
    }

    public RollBuilder withDifficulty(byte difficulty) {
        this.isWithDifficulty = true;
        this.difficulty = difficulty;
        return this;
    }

    public RollBuilder withSpecialization() {
        this.isSpecialised = true;
        return this;
    }

    public RollBuilder forDamage() {
        this.isDamageRoll = true;
        return this;
    }

    public Roll build() {
        if (isWithDifficulty) {
            if (isDamageRoll) {
                return new RollForDamage(quantity, authorId, difficulty);
            } else if (isSpecialised) {
                return new RollWithSpecialisation(quantity, authorId, difficulty);
            } else {
                return new RollWithDifficulty(quantity, authorId, difficulty);
            }
        } else {
            return new Roll(quantity, authorId);
        }
    }
}
