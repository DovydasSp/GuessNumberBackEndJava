package eu.openg.guessnumberapi.gateway.implementation.exception;

public class PostgresqlException extends RuntimeException {

    public PostgresqlException(String reason) {
        super(reason);
    }
}
