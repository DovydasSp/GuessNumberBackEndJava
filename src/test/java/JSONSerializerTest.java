import game.RestGameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONSerializerTest {
    private Object gameEntity;
    private JacksonJSONSerializer serializer;

    @BeforeEach
    void setUp() {
        gameEntity = new RestGameEntity(1);
        serializer = new JacksonJSONSerializer();
    }

    @Test
    void serialize() {
        String expected = serializer.serialize(gameEntity);
        String actual = "{\"response\":1}";
        assertEquals(expected, actual);
    }
}
