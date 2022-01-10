package com.piotrm.wodbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Map;


@Getter
@Setter
@ToString
public class PlayerCharacter {
    private long playerId;
    @NotNull
    private String name;
    private Map<String,String> info;
    private Map<String,Byte> attributes;
    private Map<String,Byte> abilities;
    private Map<String,Byte> positions;
    private Map<String,Byte> resources;
}
