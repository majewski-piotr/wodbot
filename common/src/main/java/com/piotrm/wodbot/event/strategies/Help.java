package com.piotrm.wodbot.event.strategies;

import org.springframework.stereotype.Component;

@Component
public class Help extends MessageCreateEventStrategy {

    @Override
    public void accept(discord4j.core.event.domain.message.MessageCreateEvent event) {
        setUp(event);
        StringBuilder help = new StringBuilder();

        help.append("Simple guide. Type:\n")
                .append("`5` for simple roll,\n")
                .append("`6 7` to roll six dices with difficulty 7,\n")
                .append("`3 6 +` to roll 3 dices with difficulty six and specialisation,\n")
                .append("`4 6 d` to roll damage related roll (`1` on dice will not substract successes).");

        sendResponse(help.toString());
    }
}
