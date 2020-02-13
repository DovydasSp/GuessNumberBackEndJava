package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuessValidatorTest {
    private GuessValidatorGateway guessValidator;

    @BeforeEach
    void setUp() {
        guessValidator = new GuessValidatorGateway();
    }

    @Test
    void isGuessCorrect() {
        assertTrue(guessValidator.isGuessCorrect(10,10));
        assertFalse(guessValidator.isGuessCorrect(1,10));
    }

    @Test
    void isGuessBiggerThanGenerated() {
        assertTrue(guessValidator.isGuessBiggerThanGenerated(10,9));
        assertFalse(guessValidator.isGuessBiggerThanGenerated(9,10));
    }

    @Test
    void isGuessNumberInBoundaries() {
        assertTrue(guessValidator.isGuessNumberInBoundaries(Constants.MAX_NUMBER_TO_GUESS));
        assertTrue(guessValidator.isGuessNumberInBoundaries(1));
        assertTrue(guessValidator.isGuessNumberInBoundaries(5));
        assertFalse(guessValidator.isGuessNumberInBoundaries(0));
        assertFalse(guessValidator.isGuessNumberInBoundaries(Constants.MAX_NUMBER_TO_GUESS+1));
    }
}
