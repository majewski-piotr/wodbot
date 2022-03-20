package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class HelpStrategy extends BaseStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);
        StringBuilder help = new StringBuilder();

        help.append("help.banner.allUsers").append("help.roll.simple")
                .append("help.roll.difficulty").append("help.roll.specialized")
                .append("help.register").append("help.login")
                .append("help.banner.loggedUsers").append("help.create")
                .append("help.get").append("help.update")
                .append("help.update.sections")
                .append("help.settings")
                .append("help.settings.languages");

        sendResponse("help.displaying" + ":\n" + help);
    }
}
