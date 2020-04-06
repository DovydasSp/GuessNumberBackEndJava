package eu.openg.guessnumberapi.usecase.api;

public class BoundaryGame {
    private final int gameId;
    private final int guessCount;
    private final int actualNumber;

    public BoundaryGame(int gameId, int guessCount, int actualNumber) {
        this.gameId = gameId;
        this.guessCount = guessCount;
        this.actualNumber = actualNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public int getActualNumber() {
        return actualNumber;
    }
}
