package com.piotrm.wodbot.cloud.model.request;

public class User {
    private String avatar;
    private String avatar_decoration;
    private String discriminator;
    private String id;
    private byte public_flags;
    private String username;

    @Override
    public String toString() {
        return "User{" +
                "avatar='" + avatar + '\'' +
                ", avatar_decoration='" + avatar_decoration + '\'' +
                ", discriminator='" + discriminator + '\'' +
                ", id='" + id + '\'' +
                ", public_flags=" + public_flags +
                ", username='" + username + '\'' +
                '}';
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_decoration() {
        return avatar_decoration;
    }

    public void setAvatar_decoration(String avatar_decoration) {
        this.avatar_decoration = avatar_decoration;
    }

    public String getDiscriminator() {
        return discriminator;
    }

    public void setDiscriminator(String discriminator) {
        this.discriminator = discriminator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public byte getPublic_flags() {
        return public_flags;
    }

    public void setPublic_flags(byte public_flags) {
        this.public_flags = public_flags;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
