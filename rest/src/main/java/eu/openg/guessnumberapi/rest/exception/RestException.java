package eu.openg.guessnumberapi.rest.exception;

public class RestException extends RuntimeException {
    private final int status;
    private final RestErrorResponse response;

    RestException(int status, RestErrorResponse response) {
        this.status = status;
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public RestErrorResponse getErrorResponse() {
        return response;
    }
}
