package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.piotrm.wodbot.cloud.model.response.ApiGatewayResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Handler implements RequestHandler<APIGatewayProxyRequestEvent, ApiGatewayResponse> {

    //This approach apparently saves execution time on cold starts
    private static final Logger logger = LoggerFactory.getLogger(Handler.class);
    private static final BotConfiguration botConfiguration = new BotConfiguration();
    private static final Bot bot = new Bot(botConfiguration);

    public ApiGatewayResponse handleRequest(APIGatewayProxyRequestEvent event, Context context) {

        logger.debug("Received event: " + event);
        return bot.handleRequest(event);
    }
}
