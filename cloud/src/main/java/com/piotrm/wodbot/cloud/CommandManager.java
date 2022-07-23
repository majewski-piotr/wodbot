package com.piotrm.wodbot.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.piotrm.wodbot.cloud.model.request.*;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import com.piotrm.wodbot.roll.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.piotrm.wodbot.cloud.Bot.getErrorResponse;

public class CommandManager {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    private static final int INTERACTION_PONG = 1;
    private static final int INTERACTION_APPLICATION_COMMAND = 2;
    private static final String OPTION_QUANTITY = "quantity";
    private static final String OPTION_DIFFICULTY = "difficulty";

    private static final String COMMAND_ROLL = "roll";
    private static final String COMMAND_ROLL_WITH_SPECIALIZATION = "roll_with_specialization";
    private static final String COMMAND_ROLL_FOR_DAMAGE = "roll_for_damage";


    public ApiGatewayResponse respondToRequest(Body requestBody) throws JsonProcessingException {

        switch (requestBody.getType()) {
            case INTERACTION_PONG:
                return getPongResponse();
            case INTERACTION_APPLICATION_COMMAND:
                return getApplicationCommandResponse(requestBody);
            default:
                String message = "Cannot parse, unknown interaction type";
                logger.error(message);
                return getErrorResponse(message, 422);
        }
    }

    private ApiGatewayResponse getApplicationCommandResponse(Body requestBody) throws JsonProcessingException {

        String authorId = requestBody.getMember().getUser().getId();
        RollBuilder rollBuilder = new RollBuilder(authorId);

        Data data = requestBody.getData();
        String commandName = data.getName();
        switch (commandName) {
            case COMMAND_ROLL_WITH_SPECIALIZATION:
                rollBuilder = rollBuilder.withSpecialization();
                break;
            case COMMAND_ROLL_FOR_DAMAGE:
                rollBuilder = rollBuilder.forDamage();
                break;
            case COMMAND_ROLL:
                break;
            default:
                String message = "Cannot parse, unknown command";
                logger.error(message);
                return getErrorResponse(message, 422);
        }

        for (Option option : data.getOptions()) {
            switch (option.getName()) {
                case OPTION_QUANTITY:
                    rollBuilder = rollBuilder.withQuantity(option.getValue());
                    break;
                case OPTION_DIFFICULTY:
                    rollBuilder = rollBuilder.withDifficulty(option.getValue());
                    break;
                default:
                    String message = "Cannot parse, unknown option";
                    logger.error(message);
                    return getErrorResponse(message, 422);
            }
        }

        Roll roll = rollBuilder.build();
        roll.roll();

        return getChatInputResponse(roll.toString());
    }

    private ApiGatewayResponse getPongResponse() {
        logger.debug("pong request");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ObjectNode responseBody = mapper.createObjectNode();
        responseBody.put("type", 1);

        return ApiGatewayResponse.builder()
                .withHeaders(headers)
                .withBody(responseBody.toString())
                .withStatusCode(200)
                .build();
    }

    private ApiGatewayResponse getChatInputResponse(String value) {
        logger.debug("chatinput request");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        ObjectNode responseBody = mapper.createObjectNode();
        responseBody.put("type", 4);
        ObjectNode responseData = mapper.createObjectNode();
        responseData.put("content", value);
        responseBody.put("data", responseData);

        return ApiGatewayResponse.builder()
                .withHeaders(headers)
                .withBody(responseBody.toString())
                .withStatusCode(200)
                .build();
    }
}
