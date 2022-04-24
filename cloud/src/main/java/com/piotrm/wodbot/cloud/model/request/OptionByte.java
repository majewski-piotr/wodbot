package com.piotrm.wodbot.cloud.model.request;

public class OptionByte implements Option {
    private String name;
    private byte type;
    private byte value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Options{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
