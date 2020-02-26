package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;

import java.util.Optional;

import static java.util.Objects.isNull;

public class GuessResponseConverter implements RestResponseConverter {

    public RestGuessResponse convert(BoundaryGuessResponse guessResponse) {
        if (isNull(guessResponse))
            return null;
        return Optional.of(guessResponse.getStatus())
                .filter(st -> st != BoundaryGuessResultStatus.CORRECT)
                .map(st -> new RestGuessResponse(status2String(st)))
                .orElseGet(() -> new RestGuessResponse(guessResponse.getNumberOfGuesses()));
    }

    private String status2String(BoundaryGuessResultStatus status) {
        return Optional.ofNullable(status).map(Enum::toString).orElse(null);
    }
}
