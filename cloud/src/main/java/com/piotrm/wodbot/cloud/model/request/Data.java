package com.piotrm.wodbot.cloud.model.request;

public class Data {
    private String guild_id;
    private String id;
    private String name;
    private Options[] options;
    private byte type;

    public String getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(String guild_id) {
        this.guild_id = guild_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Options[] getOptions() {
        return options;
    }

    public void setOptions(Options[] options) {
        this.options = options;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
