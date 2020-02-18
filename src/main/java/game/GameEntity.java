package game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameEntity {
    private final Integer gameId;
    private final Integer guessCount;
    private final Integer generatedNumber;

    public GameEntity(@JsonProperty("gameId") Integer gameId,
                      @JsonProperty("guessCount") Integer guessCount,
                      @JsonProperty("generatedNumber") Integer generatedNumber) {
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
