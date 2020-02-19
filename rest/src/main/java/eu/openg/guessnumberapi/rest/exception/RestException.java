package eu.openg.guessnumberapi.rest.exception;

public class RestException extends RuntimeException {
    private final int status;
    private final RestErrorResponseEntity responseEntity;

    RestException(int status, RestErrorResponseEntity responseEntity) {
        this.status = status;
        this.responseEntity = responseEntity;
    }

    public int getStatus() {
        return status;
    }

    public RestErrorResponseEntity getErrorResponseEntity() {
        return responseEntity;
    }
}
