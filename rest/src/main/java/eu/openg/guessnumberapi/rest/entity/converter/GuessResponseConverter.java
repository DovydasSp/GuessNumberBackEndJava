package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGameFinishedResponse;
import eu.openg.guessnumberapi.rest.entity.RestGameOngoingResponse;
import eu.openg.guessnumberapi.rest.entity.RestGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;

import java.util.Optional;

import static java.util.Objects.isNull;

public class GuessResponseConverter implements RestResponseConverter {

    public RestGuessResponse convert(BoundaryGuessResponse guessResponse) {
        if (isNull(guessResponse))
            return null;
        BoundaryGuessResultStatus status = guessResponse.getStatus();
        if (status == BoundaryGuessResultStatus.CORRECT)
            return new RestGameFinishedResponse(guessResponse.getNumberOfGuesses());
        else
            return new RestGameOngoingResponse(status2String(status));
    }

    private String status2String(BoundaryGuessResultStatus status) {
        return Optional.ofNullable(status).map(Enum::toString).orElse(null);
    }
}
