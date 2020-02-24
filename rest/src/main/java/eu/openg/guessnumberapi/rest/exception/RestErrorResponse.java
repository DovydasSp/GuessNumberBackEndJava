package eu.openg.guessnumberapi.rest.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestErrorResponse {
    private final String message;

    public RestErrorResponse(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
