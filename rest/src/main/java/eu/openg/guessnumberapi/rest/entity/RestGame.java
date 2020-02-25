package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGame {
    private final int gameId;
    private final Integer guessCount;
    private final Integer actualNumber;

    public RestGame(@JsonProperty("gameId") int gameId) {
        this(gameId, null, null);
    }

    public RestGame(@JsonProperty("gameId") int gameId,
                    @JsonProperty("guessCount") Integer guessCount,
                    @JsonProperty("actualNumber") Integer actualNumber) {
        this.gameId = gameId;
        this.guessCount = guessCount;
        this.actualNumber = actualNumber;
    }

    public int returnGameId() {
        return gameId;
    }

    public int returnGuessCount() {
        return guessCount;
    }

    public int returnActualNumber() {
        return actualNumber;
    }
}
