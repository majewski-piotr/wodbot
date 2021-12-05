package com.wodbot.service;

import com.wodbot.authentication.Authenticator;
import com.wodbot.dao.DAO;
import com.wodbot.entity.Player;
import discord4j.common.util.Snowflake;
import discord4j.core.object.entity.User;

public class PlayerService {
    private static PlayerService instance;
    private DAO dao;
    private Authenticator authenticator;

    public void logIn(User user){
        authenticator.logIn(user);
        dao.savePlayer(new Player(user));
    }

    public void register(User user) {dao.savePlayer(new Player(user));}

    public Player getPlayer(Snowflake id){
        return dao.getPlayer(id);
    }

    public void updatePlayer(Player player){
        dao.savePlayer(player);
    }


    public static PlayerService getInstance(DAO dao) {
        PlayerService result = instance;
        if (instance != null) {
            return result;
        }
        synchronized (PlayerService.class) {
            if (instance == null) {
                instance = new PlayerService();
                instance.dao = dao;
                instance.authenticator = Authenticator.getInstance();
            }
            return instance;
        }
    }
    public static PlayerService getInstance() {
        synchronized (PlayerService.class){
            return instance;
        }
    }
}
