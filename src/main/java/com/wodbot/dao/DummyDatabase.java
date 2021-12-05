package com.wodbot.dao;

import com.wodbot.entity.Player;
import discord4j.common.util.Snowflake;

import java.util.HashMap;

public class DummyDatabase implements DAO {

    private HashMap<Snowflake, Player> players = new HashMap<>();

    private static DummyDatabase instance;

    @Override
    public Player getPlayer(Snowflake Id) {
        return players.get(Id);
    }

    @Override
    public void savePlayer(Player player) {
        players.put(player.getUser().getId(),player);
    }

    public static DummyDatabase getInstance() {
        DummyDatabase result = instance;
        if (instance != null) {
            return result;
        }
        synchronized (DummyDatabase.class) {
            if (instance == null) {
                instance = new DummyDatabase();
            }
            return instance;
        }
    }
}
