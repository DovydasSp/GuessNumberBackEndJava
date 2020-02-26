package eu.openg.guessnumberapi.rest.exception;

public class ServerErrorException extends RestException {
    public ServerErrorException() {
        super(500, new RestErrorResponse("System error"));
    }
}
