package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGuessResponse {
    private final Integer numberOfGuesses;
    private final String message;

    public RestGuessResponse(int numberOfGuesses) {
        this(null, numberOfGuesses);
    }

    public RestGuessResponse(String message) {
        this(message, null);
    }

    private RestGuessResponse(String message, Integer numberOfGuesses) {
        this.message = message;
        this.numberOfGuesses = numberOfGuesses;
    }

    @JsonProperty("numberOfGuesses")
    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }
}
