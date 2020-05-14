package eu.openg.guessnumberapi.usecase.api;

public class BoundaryGuessResponse {
    private final BoundaryGuessResultStatus status;
    private final int numberOfGuesses;

    public BoundaryGuessResponse(BoundaryGuessResultStatus status, int numberOfGuesses) {
        this.status = status;
        this.numberOfGuesses = numberOfGuesses;
    }

    public BoundaryGuessResultStatus getStatus() {
        return status;
    }

    public int getNumberOfGuesses() {
        return numberOfGuesses;
    }
}
