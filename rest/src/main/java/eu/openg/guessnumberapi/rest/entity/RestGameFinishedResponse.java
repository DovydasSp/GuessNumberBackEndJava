package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGameFinishedResponse implements RestGuessResponse {
    private final Integer numberOfGuesses;

    public RestGameFinishedResponse(@JsonProperty("numberOfGuesses") Integer numberOfGuesses) {
        this.numberOfGuesses = numberOfGuesses;
    }

    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }
}
