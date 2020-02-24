package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGame {
    private final int gameId;
    private final Integer guessCount;
    private final Integer generatedNumber;

    public RestGame(@JsonProperty("gameId") int gameId,
                    @JsonProperty("guessCount") Integer guessCount,
                    @JsonProperty("generatedNumber") Integer generatedNumber) {
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
