package com.piotrm.wodbot.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.piotrm.wodbot.cloud.model.request.Body;
import com.piotrm.wodbot.cloud.model.request.Data;
import com.piotrm.wodbot.cloud.model.request.Options;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import com.piotrm.wodbot.roll.BasicRoll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static com.piotrm.wodbot.cloud.CommandManager.Command.ROLL;

public class CommandManager {
    private final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(CommandManager.class);

    public enum Command {
        PONG(1),
        ROLL(2);
        final int type;

        Command(int type) {
            this.type = type;
        }
        @Override
        public String toString(){
           return this.name().toLowerCase();
        }
    }

    public ApiGatewayResponse respondToRequest(Body requestBody) throws JsonProcessingException {
        logger.debug(mapper.writeValueAsString(requestBody));
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ObjectNode responseBody = mapper.createObjectNode();

        if (requestBody.getType() == Command.PONG.type) {
            logger.debug("pong request");
            responseBody.put("type", 1);
            return ApiGatewayResponse.builder()
                    .withHeaders(headers)
                    .withBody(responseBody.toString())
                    .withStatusCode(200)
                    .build();
        } else if (requestBody.getType() == Command.ROLL.type) {
            Data data = requestBody.getData();
            if (data.getName().equals(ROLL.toString())) {
                logger.debug("roll request");
                Options[] options = data.getOptions();
                Options diceQuantity = options[0];
                BasicRoll roll = new BasicRoll(diceQuantity.getValue(), "Unknown");
                roll.roll();

                responseBody.put("type", 4);
                ObjectNode responseData = mapper.createObjectNode();
                responseData.put("content", roll.toString());
                responseBody.put("data", responseData);

                return ApiGatewayResponse.builder()
                        .withHeaders(headers)
                        .withBody(mapper.writeValueAsString(responseBody))
                        .withStatusCode(200)
                        .build();
            }
        }
        return ApiGatewayResponse.builder()
                .withHeaders(headers)
                .withBody("Cannot process this request")
                .withStatusCode(400)
                .build();
    }

}
