package com.piotrm.wodbot.cloud;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.lang3.time.StopWatch;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.internal.http.loader.DefaultSdkHttpClientBuilder;
import software.amazon.awssdk.http.ExecutableHttpRequest;
import software.amazon.awssdk.http.HttpExecuteRequest;
import software.amazon.awssdk.http.HttpExecuteResponse;
import software.amazon.awssdk.http.SdkHttpClient;
import software.amazon.awssdk.http.async.SdkAsyncHttpClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.http.HttpClient;
import java.util.HashMap;

import static com.piotrm.wodbot.cloud.SecretProvider.getSecret;

public class LambdaAPI implements RequestHandler<HashMap, ApiGatewayResponse> {

    private static final Logger logger = LoggerFactory.getLogger(LambdaAPI.class);

    @Override
    public ApiGatewayResponse handleRequest(HashMap event, Context context) {


        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.debug("SecretsManagerClient build time: " + stopWatch.getTime());


        RequestValidator requestValidator = new RequestValidator(
                getSecret(
                        System.getenv("SECRET_NAME_PUBLIC_KEY")
                )
        );
        Application application = new Application(
                event,
                requestValidator
        );

        logger.debug("Application run!");
        return application.run();
    }
}