package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGameEntity {
    private final Integer gameId;
    private final Integer guessCount;
    private final Integer generatedNumber;

    public RestGameEntity(@JsonProperty("gameId") Integer gameId,
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
