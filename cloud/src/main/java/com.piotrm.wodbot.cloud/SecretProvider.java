package com.piotrm.wodbot.cloud;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

public class SecretProvider {
    AWSSecretsManager secretsManager;

    SecretProvider(String region) {
        secretsManager = AWSSecretsManagerClientBuilder.standard().withRegion(region).build();
    }

    public String getSecret(String secretName) {

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest().withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = this.secretsManager.getSecretValue(getSecretValueRequest);

        return getSecretValueResult.getSecretString();
    }
}
