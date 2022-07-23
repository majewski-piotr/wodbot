package com.piotrm.wodbot.cloud.model.request;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class Option {
    private String name;
    private byte type;
    private byte value;
}
