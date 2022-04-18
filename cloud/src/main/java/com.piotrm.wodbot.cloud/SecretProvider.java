package com.piotrm.wodbot.cloud;


import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretProvider {

    public static final SecretsManagerClient secretsClient = SecretsManagerClient.builder()
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .region(Region.EU_CENTRAL_1)
            .build();

    private static final Logger logger = LoggerFactory.getLogger(SecretProvider.class);

    public static String getSecret(String secretName) {
        StopWatch stopWatch = new StopWatch();

        logger.debug("retriving secret: " + secretName);
        stopWatch.start();
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
        GetSecretValueResponse getSecretValueResponse = secretsClient.getSecretValue(getSecretValueRequest);
        stopWatch.stop();
        logger.debug("secret retirie time: " + stopWatch.getTime());
        return getSecretValueResponse.secretString();
    }
}
