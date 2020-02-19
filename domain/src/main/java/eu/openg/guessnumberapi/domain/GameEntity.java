package eu.openg.guessnumberapi.domain;

public class GameEntity {
    private final Integer gameId;
    private final Integer guessCount;
    private final Integer generatedNumber;

    public GameEntity(Integer gameId, Integer guessCount, Integer generatedNumber) {
        this.gameId = gameId;
        this.guessCount = guessCount;
        this.generatedNumber = generatedNumber;
    }

    public Integer returnGameId() {
        return gameId;
    }

    public Integer returnGuessCount() {
        return guessCount;
    }

    public Integer returnGeneratedNumber() {
        return generatedNumber;
    }
}
