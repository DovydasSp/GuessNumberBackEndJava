package eu.openg.guessnumberapi.rest.exception;

import static java.lang.String.format;

public class NotFoundException extends RestException {
    public NotFoundException(String param) {
        super(404, new RestErrorResponse(format("%s not found", param)));
    }
}
