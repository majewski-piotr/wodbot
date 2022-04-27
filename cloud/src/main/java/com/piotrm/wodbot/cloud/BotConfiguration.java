package com.piotrm.wodbot.cloud;

import com.piotrm.wodbot.cloud.validation.RequestValidator;
import static com.piotrm.wodbot.cloud.SystemProvider.EnvironmentVariabes.PUBLIC_KEY;

public class BotConfiguration {
    private static final SystemProvider systemProvider = new SystemProvider();
    private static final RequestValidator requestValidator = new RequestValidator(systemProvider.getEnv(PUBLIC_KEY));
    public RequestValidator getRequestValidator() {
        return requestValidator;
    }
}
