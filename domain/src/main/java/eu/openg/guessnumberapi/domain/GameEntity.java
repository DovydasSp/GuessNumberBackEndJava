package eu.openg.guessnumberapi.domain;

public class GameEntity {
    private final int gameId;
    private final int guessCount;
    private final int generatedNumber;

    public GameEntity(int gameId, int guessCount, int generatedNumber) {
        this.gameId = gameId;
        this.guessCount = guessCount;
        this.generatedNumber = generatedNumber;
    }

    public int returnGameId() {
        return gameId;
    }

    public int returnGuessCount() {
        return guessCount;
    }

    public int returnGeneratedNumber() {
        return generatedNumber;
    }
}
