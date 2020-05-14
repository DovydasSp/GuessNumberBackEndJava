package eu.openg.guessnumberapi.rest.exception;

public class ServerErrorException extends RestException {
    public ServerErrorException() {
        this("System error.");
    }

    public ServerErrorException(String message) {
        super(500, new RestErrorResponse(message));
    }
}
