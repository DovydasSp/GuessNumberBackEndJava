package eu.openg.guessnumberapi.rest.exception;

import static java.lang.String.format;

public class MissingParamException extends RestException {

    public MissingParamException(String param) {
        super(400, new RestErrorResponse(format("Missing %s parameter", param)));
    }
}
