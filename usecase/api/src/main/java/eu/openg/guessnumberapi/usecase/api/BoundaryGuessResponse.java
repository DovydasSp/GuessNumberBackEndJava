package eu.openg.guessnumberapi.usecase.api;

public class BoundaryGuessResponse {
    private final String message;
    private final int numberOfGuesses;

    public BoundaryGuessResponse(String message, int numberOfGuesses) {
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
