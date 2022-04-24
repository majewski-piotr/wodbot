package com.piotrm.wodbot.cloud;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.piotrm.wodbot.cloud.model.request.*;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import com.piotrm.wodbot.roll.Roll;
import com.piotrm.wodbot.roll.RollForDamage;
import com.piotrm.wodbot.roll.RollWithDifficulty;
import com.piotrm.wodbot.roll.RollWithSpecialisation;
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
                Option[] options = data.getOptions();

                Roll roll = null;

                //Basic roll
                if(options.length==1){
                    OptionByte diceQuantity = (OptionByte)options[0];
                    roll = new Roll(diceQuantity.getValue(), requestBody.getMember().getUser().getId());

                    //with difficulty
                } else if(options.length == 2){
                    OptionByte diceQuantity = (OptionByte)options[0];
                    OptionByte difficulty = (OptionByte)options[1];
                    roll = new RollWithDifficulty(diceQuantity.getValue(), requestBody.getMember().getUser().getId(),difficulty.getValue());
                }else if(options.length==3){
                    OptionByte diceQuantity = (OptionByte)options[0];
                    OptionByte difficulty = (OptionByte)options[1];
                    OptionBool special = (OptionBool)options[2];
                    if(special.getName().equals("specialisation")){
                        roll = new RollWithSpecialisation(diceQuantity.getValue(), requestBody.getMember().getUser().getId(),difficulty.getValue());
                    }else if(special.getName().equals("health-roll")){
                        roll = new RollForDamage(diceQuantity.getValue(), requestBody.getMember().getUser().getId(),difficulty.getValue());

                    }

                }

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
