package com.piotrm.wodbot.desktop;

import com.piotrm.wodbot.event.listeners.EventListener;
import com.piotrm.wodbot.desktop.ui.DiscordTokenSetup;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@ComponentScan("com.piotrm.wodbot")
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger(BotConfiguration.class);

    private String token;

    BotConfiguration() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(new DiscordTokenSetup());
        Properties properties = DiscordTokenSetup.getProperties();
        while (DiscordTokenSetup.isRunning() && properties.size() != 1){
            Thread.sleep(500);
            System.out.println("waiting for input ...");
        }
        if(properties.size()==0){
            System.exit(0);
        }
        token = properties.getProperty(DiscordTokenSetup.DISCORD_TOKEN);
    }

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;


        try {
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            for (EventListener<T> listener : eventListeners) {
                client.on(listener.getEventType()).subscribe(listener);
            }
        } catch (Exception exception) {
            log.error("Be sure to use a valid bot token!", exception);
            System.exit(1);
        }

        return client;
    }

}