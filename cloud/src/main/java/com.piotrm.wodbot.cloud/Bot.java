package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import com.piotrm.wodbot.cloud.model.request.Body;
import com.piotrm.wodbot.cloud.validation.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    private final RequestValidator requestValidator;

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);;


    public Bot(BotConfiguration botConfiguration) {
        this.requestValidator = botConfiguration.getRequestValidator();
    }

    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event) {
        logger.error(event.toString());
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,true);
        Body requestBody = null;
        try {
            requestBody = mapper.readValue(event.getBody(), Body.class);
        } catch (JsonProcessingException e) {
            logger.error("Failed to read body from request",e);
        }

        logger.debug("BODY: "+requestBody);



        ApiGatewayResponse response = null;
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        ObjectNode body = mapper.createObjectNode();
        body.put("type", 4);
        ObjectNode data = mapper.createObjectNode();
        data.put("tts",false);
        data.put("content","tak będą się wyświetlać rzuty. zajebiście.");
        data.put("embeds",mapper.createObjectNode().arrayNode());
        ObjectNode allowed_mentions = mapper.createObjectNode();
        allowed_mentions.put("parse",mapper.createObjectNode().arrayNode());
        data.put("allowed_mentions",allowed_mentions);
        body.put("data",data);

        boolean isValidated = false;
        try {
            isValidated = requestValidator.validateRequest(event.getHeaders(), mapper.writeValueAsString(requestBody));
        } catch (JsonProcessingException e) {
            logger.error("Failed to write request bofy as a String",e);
        }


        if (!isValidated){
            logger.debug("option 1");
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody("validation failed")
                    .withStatusCode(401)
                    .build();
        } else if(requestBody.getData().getName().equals("roll")){
            logger.debug("option 2");
            try {
                response = ApiGatewayResponse.builder()
                        .withHeaders(headers)
                        .withBody(mapper.writeValueAsString(body))
                        .withStatusCode(200)
                        .build();
            } catch (JsonProcessingException e) {
                logger.error("Failed to write response as string",e);
            }
        } else {
            logger.debug("option 3");
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody(requestBody.toString())
                    .withStatusCode(200)
                    .build();
        }

        logger.info("response body: " + response.getBody());
        return response;
    }
}
