package com.piotrm.wodbot.event.strategies;

import com.piotrm.wodbot.service.UserService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


@Component
public class RegisterStrategy implements EventStrategy<MessageCreateEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void accept(MessageCreateEvent event) {
        Message message = event.getMessage();
        MessageChannel channel = message.getChannel().block();

        String [] data = message.getContent().split(" ");
        String response = null;
        try {
            response = userService.saveNewUser(data[1], data[2], data[3]) ? "Zapisano nowego użytkonika" : "Coś poszło nie tak";
        } catch (ConstraintViolationException e){
            StringBuilder errorMsg = new StringBuilder("Błędne: ");
            for(ConstraintViolation violation : e.getConstraintViolations()){
                errorMsg.append(violation.getPropertyPath());
            }
            response = errorMsg.toString();
        }
        channel.createMessage(response).block();
    }
}
