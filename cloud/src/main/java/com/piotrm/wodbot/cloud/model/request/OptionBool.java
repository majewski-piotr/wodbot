package com.piotrm.wodbot.cloud.model.request;


public class OptionBool implements Option {

    private String name;
    private byte type;
    private boolean value;

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

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
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
