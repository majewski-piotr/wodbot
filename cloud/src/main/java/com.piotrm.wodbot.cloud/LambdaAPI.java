package com.piotrm.wodbot.cloud;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;

import static com.piotrm.wodbot.cloud.SystemProvider.EnvironmentVariabes.*;

public class LambdaAPI implements RequestHandler<HashMap, ApiGatewayResponse> {

    private static final Logger logger = LoggerFactory.getLogger(LambdaAPI.class);

    @Override
    public ApiGatewayResponse handleRequest(HashMap event, Context context) {
        logger.info("Setting up System Provider");
        SystemProvider systemProvider = new SystemProvider();
        logger.info("Setting up Secret Provider");
        logger.info("Using sys variable AWS_REGION: " + systemProvider.getEnv(AWS_REGION));
        SecretProvider secretProvider = new SecretProvider(
                systemProvider.getEnv(AWS_REGION)
        );
        logger.info("Setting up Request Validator");
        logger.info("Using sys variable SECRET_NAME_PUBLIC_KEY: " + SECRET_NAME_PUBLIC_KEY.name());
        RequestValidator requestValidator = new RequestValidator(
                secretProvider.getSecret(
                        systemProvider.getEnv(SECRET_NAME_PUBLIC_KEY)
                )
        );
        logger.info("Setting up Application");
        Application application = new Application(
                event,
                requestValidator
        );

        logger.info("Application run!");
        return application.run();
    }
}