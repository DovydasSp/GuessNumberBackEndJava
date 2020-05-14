package eu.openg.guessnumberapi.rest.exception;

import static java.lang.String.format;

public class InvalidParamException extends RestException {

    public InvalidParamException(String param) {
        super(400, new RestErrorResponse(format("Invalid %s parameter", param)));
    }
}
