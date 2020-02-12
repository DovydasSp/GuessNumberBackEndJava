import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameLogicTest {
    private GameLogic gameLogic;

    @Before
    public void setUp(){
        gameLogic = new GameLogic();
    }

    @Test
    public void generateNumber() {
        gameLogic.generateNumber();
        assertTrue(gameLogic.returnGeneratedNumber() <= Constants.MAX_NUMBER_TO_GUESS);
    }

    @Test
    public void guessCount(){
        gameLogic.isGuessCorrect(gameLogic.returnGeneratedNumber()-1);
        gameLogic.isGuessCorrect(gameLogic.returnGeneratedNumber()+1);
        assertEquals(gameLogic.returnGuessCount(), 2);
    }

    @Test
    public void checkIfGuessed() {
        assertTrue(gameLogic.isGuessCorrect(gameLogic.returnGeneratedNumber()));
        assertFalse(gameLogic.isGuessCorrect(gameLogic.returnGeneratedNumber()+1));
    }

    @Test
    public void isGuessBiggerThanGenerated() {
        assertTrue(gameLogic.isGuessBiggerThanGenerated(gameLogic.returnGeneratedNumber()+1));
        assertFalse(gameLogic.isGuessBiggerThanGenerated(gameLogic.returnGeneratedNumber()-1));
    }
}
