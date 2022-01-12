package com.piotrm.wodbot;

import com.piotrm.wodbot.dao.PlayerCharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class DiscordBotApp {

	@Autowired
	PlayerCharacterRepository playerCharacterRepository;

	public static void main(String[] args) {
		SpringApplication.run(DiscordBotApp.class, args);
	}


}
