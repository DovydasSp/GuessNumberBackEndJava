package eu.openg.guessnumberapi.domain;

public class Game {
    private final int gameId;
    private final int guessCount;
    private final int actualNumber;

    public Game(int actualNumber) {
        this(0, 0, actualNumber);
    }

    public Game(int gameId, int guessCount, int actualNumber) {
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
