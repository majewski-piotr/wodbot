package com.piotrm.wodbot.cloud.model.response;

import java.util.Collections;
import java.util.Map;

public class ApiGatewayResponse {
    private final int statusCode;
    private final String body;
    private final Map<String, String> headers;

    public ApiGatewayResponse(int statusCode, String body, Map<String, String> headers) {
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private int statusCode = 200;
        private Map<String, String> headers = Collections.emptyMap();
        private String body = "Default body";

        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public ApiGatewayResponse build() {
            return new ApiGatewayResponse(statusCode, body, headers);
        }
    }
}
