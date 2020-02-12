import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameEntityTest {
    private GameEntity gameEntity;

    @Before
    public void setUp(){
        gameEntity = new GameEntity();
    }

    @Test
    public void returnGuessCount() {
        assertEquals(gameEntity.returnGuessCount(), 0);
    }

    @Test
    public void returnGeneratedNumber() {
        assertEquals(gameEntity.returnGeneratedNumber(), 0);
    }

    @Test
    public void setGuessCount() {
        gameEntity.setGuessCount(5);
        assertEquals(gameEntity.returnGuessCount(), 5);
    }

    @Test
    public void setGeneratedNumber() {
        gameEntity.setGeneratedNumber(4);
        assertEquals(gameEntity.returnGeneratedNumber(), 4);
    }
}
