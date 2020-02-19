package eu.openg.guessnumberapi.rest.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestErrorResponseEntity {
    private final String message;

    public RestErrorResponseEntity(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
