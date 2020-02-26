package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;

public interface RestResponseConverter {
    RestGuessResponse convert(BoundaryGuessResponse guessResponse);
}
