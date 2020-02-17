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
        Map<String, Integer> values = createInputMap("gameId", 456);
        String expected = "{\"gameId456\":456}";

        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }

    @Test
    void getEmptyStringWhenSerializingNull() {
        Optional<String> actual = serializer.serialize(null);

        assertFalse(actual.isPresent());
    }

    @Test
    void doNotSerializeNullValues() {
        Map<String, Integer> values = createInputMap("gameId", 456, null);

        String expected = "{\"gameId456\":456}";

        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }

    private Map<String, Integer> createInputMap(String name, Integer... values) {
        Map<String, Integer> map = new HashMap<>();
        for (Integer i : values) {
            map.put(name + i, i);
        }
        return map;
    }
}
