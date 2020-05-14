package eu.openg.guessnumberapi.rest.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JSONSerializerTest {
    private JacksonJSONSerializer serializer;

    @Mock
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        serializer = new JacksonJSONSerializer(objectMapper);
    }

    @Test
    void serializeValueMapAndVerifyThatMapperWasCalled() throws JsonProcessingException {
        Map<String, Integer> values = createInputMap(456);

        serializer.serialize(values);

        verify(objectMapper).writeValueAsString(values);
    }

    @Test
    void serializationCaughtObjectMapperException() throws JsonProcessingException {
        Map<String, Integer> values = createInputMap(456);

        when(objectMapper.writeValueAsString(values)).thenThrow(JsonProcessingException.class);

        serializer.serialize(values);

        assertThatThrownBy(() -> objectMapper.writeValueAsString(values)).isInstanceOf(JsonProcessingException.class);
    }

    @Test
    void deserializeStringAndVerifyThatMapperWasCalled() throws JsonProcessingException {
        String value = "{\"gameId\":\"456\"}";

        serializer.deserialize(value, Map.class);

        verify(objectMapper).readValue(value, Map.class);
    }

    @Test
    void deserializationCaughtObjectMapperException() throws JsonProcessingException {
        String value = "{\"gameId\":\"456\"}";

        when(objectMapper.readValue(value, Map.class)).thenThrow(JsonProcessingException.class);

        serializer.deserialize(value, Map.class);

        assertThatThrownBy(() -> objectMapper.readValue(value, Map.class)).isInstanceOf(JsonProcessingException.class);
    }

    private Map<String, Integer> createInputMap(Integer... values) {
        Map<String, Integer> map = new HashMap<>();
        for (Integer i : values) {
            map.put("gameId" + i, i);
        }
        return map;
    }
}
