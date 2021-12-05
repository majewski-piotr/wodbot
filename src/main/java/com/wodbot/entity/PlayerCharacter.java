package com.wodbot.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCharacter {

    private String name;

    private Clan clan;

    private byte Strength,Dexterity,Stamina,Manipulation,Charisma,Appearance,Perception,Intelligence,Wits;

    @Override
    public String toString() {
        return "PlayerCharacter\n" +
                "name=" + name  +
                "\nclan=" + clan +
                "\nStrength=" + Strength +
                "\nDexterity=" + Dexterity +
                "\nStamina=" + Stamina +
                "\nManipulation=" + Manipulation +
                "\nCharisma=" + Charisma +
                "\nAppearance=" + Appearance +
                "\nPerception=" + Perception +
                "\nIntelligence=" + Intelligence +
                "\nWits=" + Wits;
    }

    public enum Clan{
        BRUJAH,GANGREL,NOSFERATU,VENTRUE,TOREADOR,MALKAVIAN,TREMERE;
    }
}
