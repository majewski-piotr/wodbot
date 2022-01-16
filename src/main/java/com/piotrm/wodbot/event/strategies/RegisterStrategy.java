package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@Component
public class RegisterStrategy extends BaseStrategy {

    @Autowired
    private UserService userService;

    @Override
    public void accept(MessageCreateEvent event) {
        setUp(event);

        String email = getData()[1];
        String username = getData()[2];
        String password = getData()[3];

        String response;
        try {
            response = userService.saveNewUser(email, username, password) ? getMessage("register.success") : getMessage("register.fail");
        } catch (ConstraintViolationException e) {
            StringBuilder errorMsg = new StringBuilder(getMessage("register.invalid"));
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                errorMsg.append(violation.getPropertyPath());
            }
            response = errorMsg.toString();
        }
        sendResponse(response);
    }
}
