package com.piotrm.wodbot.cloud;

public class SystemProvider {

    public enum EnvironmentVariabes {
        AWS_REGION,
        SECRET_NAME_CLIENT_ID,
        SECRET_NAME_CLIENT_SECRET,
        SECRET_NAME_PUBLIC_KEY
    }

    public String getEnv(EnvironmentVariabes variable) {
        return System.getenv(variable.name());
    }
}
