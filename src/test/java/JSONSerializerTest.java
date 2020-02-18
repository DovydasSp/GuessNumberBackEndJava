import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JSONSerializerTest {
    private JacksonJSONSerializer serializer;

    @Mock
    private static ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        serializer = new JacksonJSONSerializer(objectMapper);
    }

    @Test
    void serialize() throws JsonProcessingException {
        Map<String, Integer> values = createInputMap(456);
        String expected = "{\"gameId456\":456}";

        when(objectMapper.writeValueAsString(values)).thenReturn(expected);
        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }

    @Test
    void getEmptyStringWhenSerializingNull() {
        Optional<String> actual = serializer.serialize(null);

        assertThat(actual).isNotPresent();
    }

    @Test
    void doNotSerializeNullValues() throws JsonProcessingException {
        Map<String, Integer> values = createInputMap(456, null);

        String expected = "{\"gameId456\":456}";

        when(objectMapper.writeValueAsString(values)).thenReturn(expected);
        Optional<String> actual = serializer.serialize(values);

        assertThat(actual).hasValue(expected);
    }

    private Map<String, Integer> createInputMap(Integer... values) {
        Map<String, Integer> map = new HashMap<>();
        for (Integer i : values) {
            map.put("gameId" + i, i);
        }
        return map;
    }
}
