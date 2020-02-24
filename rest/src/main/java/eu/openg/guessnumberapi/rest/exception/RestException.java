package eu.openg.guessnumberapi.rest.exception;

public class RestException extends RuntimeException {
    private final int status;
    private final RestErrorResponse responseEntity;

    RestException(int status, RestErrorResponse responseEntity) {
        this.status = status;
        this.responseEntity = responseEntity;
    }

    public int getStatus() {
        return status;
    }

    public RestErrorResponse getErrorResponseEntity() {
        return responseEntity;
    }
}
