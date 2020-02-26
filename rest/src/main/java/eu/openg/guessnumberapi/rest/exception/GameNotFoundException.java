package eu.openg.guessnumberapi.rest.exception;

import static java.lang.String.format;

public class GameNotFoundException extends RestException {

    public GameNotFoundException(int gameId) {
        super(404, new RestErrorResponse(format("Invalid gameID:%d parameter", gameId)));
    }
}
