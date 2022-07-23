package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final CommandManager commandManager = new CommandManager();
    private static final Class<com.piotrm.wodbot.cloud.model.request.Body> bodyClass = Body.class;


    public Bot(BotConfiguration botConfiguration) {
        this.requestValidator = botConfiguration.getRequestValidator();
    }

    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event) {

        Body requestBody = null;
        try {
            requestBody = mapper.readValue(event.getBody(), bodyClass);
        } catch (JsonProcessingException e) {
            String message = "Failed to read body from request";
            logger.error(message, e);
            return getErrorResponse(message, 400);
        }

        boolean isValidated = requestValidator.validateRequest(event.getHeaders(), event.getBody());

        ApiGatewayResponse response;
        if (!isValidated) {
            return getErrorResponse("Validation failed", 403);
        } else {
            try {
                response = commandManager.respondToRequest(requestBody);
            } catch (JsonProcessingException e) {
                String message = "Failed to parse command";
                logger.error(message, e);
                return getErrorResponse(message, 400);
            }
        }
        return response;
    }

    public static ApiGatewayResponse getErrorResponse(String message, int status) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ObjectNode responseBody = mapper.createObjectNode();
        responseBody.put("error", message);

        return ApiGatewayResponse.builder()
                .withHeaders(headers)
                .withBody(responseBody.toString())
                .withStatusCode(status)
                .build();
    }
}
