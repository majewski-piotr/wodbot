package com.piotrm.wodbot.cloud.model.request;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class User {
    private String avatar;
    private String avatar_decoration;
    private String discriminator;
    private String id;
    private byte public_flags;
    private String username;
}
