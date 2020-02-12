package game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuessValidatorTest {
    private GuessValidator guessValidator;

    @Before
    public void setUp(){
        guessValidator = new GuessValidator();
    }

    @Test
    public void isGuessCorrect() {
        assertTrue(guessValidator.isGuessCorrect(10,10));
        assertFalse(guessValidator.isGuessCorrect(1,10));
    }

    @Test
    public void isGuessBiggerThanGenerated() {
        assertTrue(guessValidator.isGuessBiggerThanGenerated(10,9));
        assertFalse(guessValidator.isGuessBiggerThanGenerated(9,10));
    }

    @Test
    public void isGuessNumberInBoundaries() {
        assertTrue(guessValidator.isGuessNumberInBoundaries(Constants.MAX_NUMBER_TO_GUESS));
        assertTrue(guessValidator.isGuessNumberInBoundaries(1));
        assertTrue(guessValidator.isGuessNumberInBoundaries(5));
        assertFalse(guessValidator.isGuessNumberInBoundaries(0));
        assertFalse(guessValidator.isGuessNumberInBoundaries(Constants.MAX_NUMBER_TO_GUESS+1));
    }
}
