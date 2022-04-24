package com.piotrm.wodbot.cloud.model.request;

import java.util.Arrays;

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

    @Override
    public String toString() {
        return "Member{" +
                "avatar='" + avatar + '\'' +
                ", communication_disabled_until='" + communication_disabled_until + '\'' +
                ", deaf=" + deaf +
                ", flags=" + flags +
                ", is_pending=" + is_pending +
                ", joined_at='" + joined_at + '\'' +
                ", mute=" + mute +
                ", nick='" + nick + '\'' +
                ", pending=" + pending +
                ", permissions='" + permissions + '\'' +
                ", premium_since='" + premium_since + '\'' +
                ", roles=" + Arrays.toString(roles) +
                ", user=" + user +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCommunication_disabled_until() {
        return communication_disabled_until;
    }

    public void setCommunication_disabled_until(String communication_disabled_until) {
        this.communication_disabled_until = communication_disabled_until;
    }

    public boolean isDeaf() {
        return deaf;
    }

    public void setDeaf(boolean deaf) {
        this.deaf = deaf;
    }

    public byte getFlags() {
        return flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public boolean isIs_pending() {
        return is_pending;
    }

    public void setIs_pending(boolean is_pending) {
        this.is_pending = is_pending;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(String joined_at) {
        this.joined_at = joined_at;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getPremium_since() {
        return premium_since;
    }

    public void setPremium_since(String premium_since) {
        this.premium_since = premium_since;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
