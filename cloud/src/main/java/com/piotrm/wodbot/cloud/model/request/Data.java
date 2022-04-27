package com.piotrm.wodbot.cloud.model.request;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class Data {
    private String guild_id;
    private String id;
    private String name;
    private Option[] options;
    private byte type;
}
