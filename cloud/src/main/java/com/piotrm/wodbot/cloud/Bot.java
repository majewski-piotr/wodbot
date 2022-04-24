package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import com.piotrm.wodbot.cloud.model.request.Body;
import com.piotrm.wodbot.cloud.validation.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Bot {
    private final RequestValidator requestValidator;

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
    ;


    public Bot(BotConfiguration botConfiguration) {
        this.requestValidator = botConfiguration.getRequestValidator();
    }

    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event) {

        Body requestBody = null;
        try {
            requestBody = mapper.readValue(event.getBody(), Body.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to read body from request", e);
        }

        logger.debug(String.valueOf(requestBody));
        ApiGatewayResponse response = null;

        boolean isValidated = false;
        try {
            isValidated = requestValidator.validateRequest(event.getHeaders(), mapper.writeValueAsString(requestBody));
        } catch (JsonProcessingException e) {
            logger.error("Failed to write request bofy as a String", e);
        }


        if (!isValidated) {
            logger.warn("request not validated");
            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody("validation failed")
                    .withStatusCode(401)
                    .build();
        } else {
            CommandManager commandManager = new CommandManager();
            try {
                response = commandManager.respondToRequest(requestBody);
            } catch (JsonProcessingException e) {
                logger.error("Failed to parse command", e);
            }
        }

        logger.info("response body: " + response.getBody());
        return response;
    }
}
