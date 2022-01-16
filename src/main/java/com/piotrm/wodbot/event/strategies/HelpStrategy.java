package com.piotrm.wodbot.event.strategies;

import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;

@Component
public class HelpStrategy extends BaseStrategy {

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);
        StringBuilder help = new StringBuilder();

        help.append(getMessage("help.banner.allUsers")).append(getMessage("help.roll.simple"))
                .append(getMessage("help.roll.difficulty")).append(getMessage("help.roll.specialized"))
                .append(getMessage("help.register")).append(getMessage("help.login"))
                .append(getMessage("help.banner.loggedUsers")).append(getMessage("help.create"))
                .append(getMessage("help.get")).append(getMessage("help.update"))
                .append(getMessage("help.update.sections"));

        sendResponse(getMessage("help.displaying")+":\n" + help);
    }
}
