package com.piotrm.wodbot.event.strategies;


import com.piotrm.wodbot.dao.UserRepository;
import com.piotrm.wodbot.model.PlayerCharacter;
import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

import static com.piotrm.wodbot.event.listeners.message.MessageCreateListener.*;

@Component
public class CharacterStrategy extends LoggedInStrategy {


    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String response = getMessage("general.fail");
        if (operation.equals(GET)) {
            response = playerCharacterService.toString(playerCharacterService.getCharacter(userId, characterName),getLocale());
        } else if (operation.equals(CREATE)) {

            response = playerCharacterService.saveNew(userId, characterName) ? getMessage("create.success") : getMessage("create.fail");
        }
        sendResponse(response);
    }
}
