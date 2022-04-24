package com.piotrm.wodbot.cloud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

public class SecretProvider {

    private SecretsManagerClient secretsClient;

    private final Logger logger = LoggerFactory.getLogger(SecretProvider.class);

    public SecretProvider(SecretsManagerClient secretsClient) {
        this.secretsClient = secretsClient;
    }

    public String getSecret(String secretName) {

        logger.debug("retrieving secret: " + secretName);
        GetSecretValueRequest getSecretValueRequest = GetSecretValueRequest.builder().secretId(secretName).build();
        GetSecretValueResponse getSecretValueResponse = secretsClient.getSecretValue(getSecretValueRequest);
        return getSecretValueResponse.secretString();
    }
}
