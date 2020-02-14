import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONSerializerTest {
    private JacksonJSONSerializer serializer;

    @BeforeEach
    void setUp() {
        serializer = new JacksonJSONSerializer();
    }

    @Test
    void serialize() {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", 456);
        String actual = "{\"gameId\":456}";

        String expected = serializer.serialize(values);

        assertEquals(expected, actual);
    }

    @Test
    void serializeNull() {
        Map<String, Integer> values = null;
        String actual = "";
        String expected = serializer.serialize(values);

        assertEquals(expected, actual);
    }
}
