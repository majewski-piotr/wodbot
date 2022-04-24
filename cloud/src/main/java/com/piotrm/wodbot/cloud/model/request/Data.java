package com.piotrm.wodbot.cloud.model.request;

import java.util.Arrays;

public class Data {
    private String guild_id;
    private String id;
    private String name;
    private Option[] options;
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

    public Option[] getOptions() {
        return options;
    }

    public void setOptions(Option[] options) {
        this.options = options;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Data{" +
                "guild_id='" + guild_id + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", options=" + Arrays.toString(options) +
                ", type=" + type +
                '}';
    }
}
