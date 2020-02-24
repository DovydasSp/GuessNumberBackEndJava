package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGuessRequest {
    private final int guessNumber;

    public RestGuessRequest(@JsonProperty("guessNumber") int guessNumber) {
        this.guessNumber = guessNumber;
    }

    public int getGuessNumber() {
        return guessNumber;
    }
}
