package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGuessResponseEntity;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;

import static java.util.Objects.isNull;

public class GuessResponseConverter {

    public RestGuessResponseEntity convert(BoundaryGuessResponse guessResponse) {
        if (isNull(guessResponse))
            return null;
        if (guessResponse.getStatus() == null)
            return new RestGuessResponseEntity(null, guessResponse.getNumberOfGuesses());
        return new RestGuessResponseEntity(guessResponse.getStatus().toString(), guessResponse.getNumberOfGuesses());
    }
}
