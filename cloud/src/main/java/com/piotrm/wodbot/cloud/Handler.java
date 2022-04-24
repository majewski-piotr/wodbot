package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

    private static final Logger logger = LoggerFactory.getLogger(Handler.class);

    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        logger.debug("configuring bot");
        BotConfiguration botConfiguration = new BotConfiguration();
        Bot bot = new Bot(botConfiguration);

        logger.debug("passing request to bot");
        return bot.handleRequest(event);
    }
}
