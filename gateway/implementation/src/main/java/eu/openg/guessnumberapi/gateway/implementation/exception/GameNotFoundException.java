package eu.openg.guessnumberapi.gateway.implementation.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
