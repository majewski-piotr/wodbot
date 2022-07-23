package com.piotrm.wodbot.cloud.model.request;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class Body {
    private String app_permissions;
    private String application_id;
    private String channel_id;
    private Data data;
    private String guild_id;
    private String guild_locale;
    private String id;
    private String locale;
    private Member member;
    private String token;
    private byte type;
    private byte version;
    private User user;
}