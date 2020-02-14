package game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GuessResponseEntity {
    private final String message;
    private final int numberOfGuesses;

    public GuessResponseEntity(@JsonProperty("message") String message,
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
