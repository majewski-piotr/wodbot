package com.wodbot.dao;

import com.wodbot.entity.Player;
import com.wodbot.entity.PlayerCharacter;
import discord4j.common.util.Snowflake;

public interface DAO {
    void savePlayer(Player player);
    Player getPlayer(Snowflake Id);
}
