package com.piotrm.wodbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@ToString
@Document
public class PlayerCharacter {
    @MongoId
    private String id;
    private long playerId;
    @NotNull
    private String name;
    private Map<String,String> info = new HashMap<>();
    private Map<String,Byte> attributes = new HashMap<>();
    private Map<String,Byte> abilities = new HashMap<>();
    private Map<String,Byte> positions = new HashMap<>();
    private Map<String,Byte> resources = new HashMap<>();
}
