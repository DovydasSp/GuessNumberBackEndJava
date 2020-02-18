import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.Optional;

public class JacksonJSONSerializer implements JSONSerializer {
    private final ObjectMapper objectMapper;

    public JacksonJSONSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<String> serialize(Object input) {
        return Optional.ofNullable(input)
                .map(object -> serializeObject(input));
    }

    private String serializeObject(Object object) {
        //TODO logger
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Map deserializeRequestBody(String body) throws JsonProcessingException {
        return objectMapper.readValue(body, Map.class);
    }
}
