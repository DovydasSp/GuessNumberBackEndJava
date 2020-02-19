package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGuessRequestEntity {
    private final int guessNumber;

    public RestGuessRequestEntity(@JsonProperty("guessNumber") int guessNumber) {
        this.guessNumber = guessNumber;
    }

    public int getGuessNumber() {
        return guessNumber;
    }
}
