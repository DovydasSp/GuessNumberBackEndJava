import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

class JSONSerializerTest {
    private JacksonJSONSerializer serializer;

    @BeforeEach
    void setUp() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        serializer = new JacksonJSONSerializer(objectMapper);
    }

    @Test
    void serialize() {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", 456);
        String expected = "{\"gameId\":456}";

        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }

    @Test
    void getEmptyStringWhenSerializingNull() {
        Map<String, Integer> values = null;
        String expected = null;
        Optional<String> actual = serializer.serialize(null);

        assertFalse(actual.isPresent());
    }

    @Test
    void doNotSerializeNullValues() {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", 456);
        values.put("gameId2", null);
        String expected = "{\"gameId\":456}";

        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }
}
