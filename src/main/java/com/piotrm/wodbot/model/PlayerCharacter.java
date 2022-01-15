package com.piotrm.wodbot.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@Document
public class PlayerCharacter {
    @Id
    private String id;
    private long playerId;
    @NotNull
    private String name;
    private Map<String, String> info = new HashMap<>();
    private Map<String, Byte> attributes = new HashMap<>();
    private Map<String, Byte> abilities = new HashMap<>();
    private Map<String, Byte> positions = new HashMap<>();
    private Map<String, Byte> resources = new HashMap<>();
}
