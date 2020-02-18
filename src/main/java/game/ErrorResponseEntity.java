package game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseEntity {
    private final String message;

    public ErrorResponseEntity(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
