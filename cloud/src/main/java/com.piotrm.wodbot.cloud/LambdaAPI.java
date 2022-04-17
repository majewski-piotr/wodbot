package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.HashMap;

import static com.piotrm.wodbot.cloud.SystemProvider.EnvironmentVariabes.*;

public class LambdaAPI implements RequestHandler<HashMap, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(HashMap event, Context context) {
        context.getLogger().log("Setting up System Provider");
        SystemProvider systemProvider = new SystemProvider();
        context.getLogger().log("Setting up Secret Provider");
        context.getLogger().log("Using sys variable AWS_REGION: " + systemProvider.getEnv(AWS_REGION));
        SecretProvider secretProvider = new SecretProvider(
                systemProvider.getEnv(AWS_REGION)
        );
        context.getLogger().log("Setting up Request Validator");
        context.getLogger().log("Using sys variable SECRET_NAME_PUBLIC_KEY: " + SECRET_NAME_PUBLIC_KEY.name());
        RequestValidator requestValidator = new RequestValidator(
                secretProvider.getSecret(
                        systemProvider.getEnv(SECRET_NAME_PUBLIC_KEY)
                )
        );
        context.getLogger().log("Setting up Application");
        Application application = new Application(
                context.getLogger(),
                event,
                requestValidator
        );

        context.getLogger().log("Application run!");
        return application.run();
    }
}