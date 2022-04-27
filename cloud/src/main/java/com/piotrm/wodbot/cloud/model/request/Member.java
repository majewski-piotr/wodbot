package com.piotrm.wodbot.cloud.model.request;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Builder(toBuilder = true)
@Getter
@ToString
@Jacksonized
public class Member {
    private String avatar;
    private String communication_disabled_until;
    private boolean deaf;
    private byte flags;
    private boolean is_pending;
    private String joined_at;
    private boolean mute;
    private String nick;
    private boolean pending;
    private String permissions;
    private String premium_since;
    private String [] roles;
    private User user;
}
