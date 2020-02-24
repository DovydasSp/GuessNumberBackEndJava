package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGuessResponse {
    private final String message;
    private final Integer numberOfGuesses;

    public RestGuessResponse(@JsonProperty("message") String message,
                             @JsonProperty("numberOfGuesses") Integer numberOfGuesses) {
        this.message = message;
        this.numberOfGuesses = numberOfGuesses;
    }

    public String getMessage() {
        return message;
    }

    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }
}
