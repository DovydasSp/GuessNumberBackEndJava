package game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RestGameEntity {
    private final Object response;

    public RestGameEntity(@JsonProperty("response") Object response) {
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }
}
