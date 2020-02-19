package eu.openg.guessnumberapi.usecase.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GuessValidatorTest {
    private GuessValidator guessValidator;

    @BeforeEach
    void setUp() {
        guessValidator = new GuessValidator();
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

}
