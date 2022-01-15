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

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(300);
        stringBuilder.append("Imię: " + name + "\n");
        stringBuilder.append("**Informacje**\n");
        for (Map.Entry<String, String> entry : info.entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**Atrybuty**\n");
        for (Map.Entry<String, Byte> entry : attributes.entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**Umiejętności**\n");
        for (Map.Entry<String, Byte> entry : abilities.entrySet()) {
            appender(stringBuilder, entry);
        }
        stringBuilder.append("**Cechy pozycji**\n");
        for (Map.Entry<String, Byte> entry : resources.entrySet()) {
            appender(stringBuilder, entry);
        }
        return stringBuilder.toString();
    }

    private void appender(StringBuilder sb, Map.Entry entry) {
        sb.append("\t").append(entry.getKey()).append(" `").append(entry.getValue()).append("`\n");
    }
}
