package com.piotrm.wodbot.cloud;

import com.piotrm.wodbot.cloud.validation.RequestValidator;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

import static com.piotrm.wodbot.cloud.SystemProvider.EnvironmentVariabes.SECRET_NAME_PUBLIC_KEY;

public class BotConfiguration {
    private static SecretProvider secretProvider;
    private static SystemProvider systemProvider;
    private static RequestValidator requestValidator;

    static{
        systemProvider = new SystemProvider();
        secretProvider = new SecretProvider(SecretsManagerClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .region(Region.EU_CENTRAL_1)
                .build());
        requestValidator = new RequestValidator(
                secretProvider.getSecret(
                        systemProvider.getEnv(SECRET_NAME_PUBLIC_KEY)
                )
        );
    }

    public SecretProvider getSecretProvider() {
        return secretProvider;
    }

    public SystemProvider getSystemProvider() {
        return systemProvider;
    }

    public RequestValidator getRequestValidator() {
        return requestValidator;
    }
}
