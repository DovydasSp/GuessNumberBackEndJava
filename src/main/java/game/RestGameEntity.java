package game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGameEntity {
    private final int gameId;
    private final int guessCount;
    private final int generatedNumber;

    public RestGameEntity(@JsonProperty("gameId") int gameId,
                          @JsonProperty("guessCount") int guessCount,
                          @JsonProperty("generatedNumber") int generatedNumber) {
        this.gameId = gameId;
        this.guessCount = guessCount;
        this.generatedNumber = generatedNumber;
    }

    public int getGameId() {
        return gameId;
    }

    public int getGuessCount() {
        return guessCount;
    }

    public int getGeneratedNumber() {
        return generatedNumber;
    }
}
