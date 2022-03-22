package com.piotrm.wodbot;

import com.piotrm.wodbot.ui.DesktopApplication;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiscordBotApp {
    public static void main(String[] args) {
        Application.launch(DesktopApplication.class,args);
//        SpringApplication.run(DiscordBotApp.class, args);
    }
}
