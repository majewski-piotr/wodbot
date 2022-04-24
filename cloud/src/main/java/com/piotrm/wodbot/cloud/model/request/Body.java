package com.piotrm.wodbot.cloud.model.request;

public class Body {
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

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getGuild_locale() {
        return guild_locale;
    }

    public void setGuild_locale(String guild_locale) {
        this.guild_locale = guild_locale;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Body{" +
                "application_id='" + application_id + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", data=" + data +
                ", guild_id='" + guild_id + '\'' +
                ", guild_locale='" + guild_locale + '\'' +
                ", id='" + id + '\'' +
                ", locale='" + locale + '\'' +
                ", member=" + member +
                ", token='" + token + '\'' +
                ", type=" + type +
                ", version=" + version +
                '}';
    }
}