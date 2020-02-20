package eu.openg.guessnumberapi.rest.exception;

import static java.lang.String.format;

public class NotFoundException extends RestException {
    public NotFoundException(String param) {
        super(404, new RestErrorResponseEntity(format("%s not found", param)));
    }
}
