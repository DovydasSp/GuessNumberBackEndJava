package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGuessResponseEntity;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;

import static java.util.Objects.nonNull;

public class GuessResponseConverter {

    public RestGuessResponseEntity convert(BoundaryGuessResponse guessResponse) {
        if (nonNull(guessResponse))
            if (guessResponse.getStatus() == null)
                return new RestGuessResponseEntity(null, guessResponse.getNumberOfGuesses());
            else
                return new RestGuessResponseEntity(guessResponse.getStatus().toString(), guessResponse.getNumberOfGuesses());
        return null;
    }
}
