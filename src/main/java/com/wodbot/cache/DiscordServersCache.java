package com.wodbot.cache;

import discord4j.common.util.Snowflake;

import java.util.HashSet;
import java.util.Set;

public class DiscordServersCache {

    private static DiscordServersCache INSTANCE;

    private Set<Snowflake> snowflalkeChannelId = new HashSet<>();

    private DiscordServersCache() {
    }

    public static DiscordServersCache getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiscordServersCache();
        }
        return INSTANCE;
    }

    public Set<Snowflake> getSnowflalkeChannelId() {
        return snowflalkeChannelId;
    }
}
