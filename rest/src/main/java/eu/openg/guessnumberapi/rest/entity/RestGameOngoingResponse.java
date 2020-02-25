package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGameOngoingResponse implements RestGuessResponse {
    private final String message;

    public RestGameOngoingResponse(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
