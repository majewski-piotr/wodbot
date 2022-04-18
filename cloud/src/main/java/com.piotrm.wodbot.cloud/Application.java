package com.piotrm.wodbot.cloud;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Application {
    private final HashMap request;
    private final RequestValidator requestValidator;

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application(HashMap request, RequestValidator requestValidator) {
        this.request = request;
        this.requestValidator = requestValidator;
    }

    public ApiGatewayResponse run(){

        // process event
        logger.info("EVENT BODY: " + request.get("body"));
        logger.info("Headers: "+request.get("headers"));

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        ApiGatewayResponse response;
        JsonObject body = new JsonObject();
        body.addProperty("type", 4);
        JsonObject data = new JsonObject();
        data.add("tts",new JsonPrimitive(false));
        data.addProperty("content","tak będą się wyświetlać rzuty. zajebiście.");
        data.add("embeds",new JsonArray());
        JsonObject allowed_mentions = new JsonObject();
        allowed_mentions.add("parse",new JsonArray());
        data.add("allowed_mentions",allowed_mentions);
        body.add("data",data);

        boolean isValidated = requestValidator.validateRequest(request);

        Gson gson = new Gson();
        if (!isValidated){
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody("validation failed")
                    .withStatusCode(401)
                    .build();
        } else if(request.toString().contains("roll")){
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody(gson.toJson(body))
                    .withStatusCode(200)
                    .build();
        } else {
            response = ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody(request.get("body").toString())
                    .withStatusCode(200)
                    .build();
        }

        logger.info("response body: " + response.getBody());
        return response;
    }
}
