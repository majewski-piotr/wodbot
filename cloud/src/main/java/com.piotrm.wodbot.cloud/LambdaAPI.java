package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.HashMap;
import java.util.Map;

public class LambdaAPI implements RequestHandler<Object, ApiGatewayResponse> {
    @Override
    public ApiGatewayResponse handleRequest(Object requestJSON, Context context) {


        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ApiGatewayResponse response = ApiGatewayResponse.builder()
                .withHeaders(headers)
                .withBody("Jacek, zdaj te mature byku")
                .withStatusCode(200)
                .build();
        return response;
    }
}