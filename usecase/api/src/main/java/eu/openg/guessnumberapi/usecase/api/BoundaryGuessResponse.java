package eu.openg.guessnumberapi.usecase.api;

public class BoundaryGuessResponse {
    private final BoundaryGuessResultStatus status;
    private final Integer numberOfGuesses;

    public BoundaryGuessResponse(BoundaryGuessResultStatus status, Integer numberOfGuesses) {
        this.status = status;
        this.numberOfGuesses = numberOfGuesses;
    }

    public BoundaryGuessResultStatus getStatus() {
        return status;
    }

    public Integer getNumberOfGuesses() {
        return numberOfGuesses;
    }
}
