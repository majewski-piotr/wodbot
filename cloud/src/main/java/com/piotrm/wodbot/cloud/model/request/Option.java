package com.piotrm.wodbot.cloud.model.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import static com.piotrm.wodbot.cloud.model.request.Option.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "name",
        visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OptionByte.class, name = QUANTITY),
        @JsonSubTypes.Type(value = OptionByte.class, name = DIFFICULTY),
        @JsonSubTypes.Type(value = OptionBool.class, name = SPECIALISATION),
        @JsonSubTypes.Type(value = OptionBool.class, name = HEALTH_ROLL)})
public interface Option {
    String QUANTITY = "quantity";
    String DIFFICULTY = "difficulty";
    String SPECIALISATION = "specialisation";
    String HEALTH_ROLL = "health-roll";
}
