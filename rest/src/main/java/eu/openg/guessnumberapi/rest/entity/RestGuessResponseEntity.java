package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGuessResponseEntity {
    private final String message;
    private final int numberOfGuesses;

    public RestGuessResponseEntity(@JsonProperty("message") String message,
                                   @JsonProperty("numberOfGuesses") int numberOfGuesses) {
        this.message = message;
        this.numberOfGuesses = numberOfGuesses;
    }

    public String getMessage() {
        return message;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }
}
