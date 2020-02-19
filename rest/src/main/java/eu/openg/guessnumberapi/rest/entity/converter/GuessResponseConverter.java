package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGuessResponseEntity;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;

public class GuessResponseConverter {

    public RestGuessResponseEntity convert(BoundaryGuessResponse guessResponse) {
        return new RestGuessResponseEntity(guessResponse.getMessage(), guessResponse.getNumberOfGuesses());
    }
}
