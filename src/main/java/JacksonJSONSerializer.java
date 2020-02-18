import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

public class JacksonJSONSerializer implements JSONSerializer {
    private static final Logger LOGGER = LogManager.getLogger(JacksonJSONSerializer.class);
    private final ObjectMapper objectMapper;

    public JacksonJSONSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Optional<String> serialize(Object input) {
        return Optional.ofNullable(input)
                .map(object -> serializeObject(input));
    }

    private String serializeObject(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            LOGGER.error("Failed to serialize object", e);
            return null;
        }
    }

    @Override
    public Map deserializeRequestBody(String body) throws JsonProcessingException {
        return objectMapper.readValue(body, Map.class);
    }
}
