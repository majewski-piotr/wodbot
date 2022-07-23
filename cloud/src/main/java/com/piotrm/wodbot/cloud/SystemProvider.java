package com.piotrm.wodbot.cloud;

public class SystemProvider {

    public enum EnvironmentVariabes {
        AWS_REGION,
        PUBLIC_KEY
    }

    public String getEnv(EnvironmentVariabes variable) {
        return System.getenv(variable.name());
    }
}
