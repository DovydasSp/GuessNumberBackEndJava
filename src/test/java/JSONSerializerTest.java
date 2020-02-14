import game.RestGameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JSONSerializerTest {
    private Object gameEntity;
    private JSONSerializer serializer;

    @BeforeEach
    void setUp() {
        gameEntity = new RestGameEntity(1, 2, 3);
        serializer = new JSONSerializer();
    }

    @Test
    void serialize() {
        String expected = serializer.serialize(gameEntity);
        String actual = "{\"gameId\":1,\"guessCount\":2,\"generatedNumber\":3}";
        assertEquals(expected, actual);
    }
}
