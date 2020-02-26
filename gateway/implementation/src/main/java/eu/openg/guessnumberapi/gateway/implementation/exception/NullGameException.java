package eu.openg.guessnumberapi.gateway.implementation.exception;

public class NullGameException extends RuntimeException {
    public NullGameException(String message) {
        super(message);
    }
}
