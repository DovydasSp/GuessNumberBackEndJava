package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessValidatorTest {
    private GuessValidator guessValidator;

    @BeforeEach
    void setUp() {
        guessValidator = new GuessValidator();
    }

    @Test
    void isGuessCorrect() {
        assertEquals(guessValidator.checkGuessAndReturnBoundaryGuessResponse(9, 9),
                BoundaryGuessResultStatus.CORRECT);
    }

    @Test
    void isGuessBiggerThanGenerated() {
        assertEquals(guessValidator.checkGuessAndReturnBoundaryGuessResponse(10, 9),
                BoundaryGuessResultStatus.LESS);
    }

    @Test
    void isGuessLowerThanGenerated() {
        assertEquals(guessValidator.checkGuessAndReturnBoundaryGuessResponse(8, 9),
                BoundaryGuessResultStatus.MORE);
    }
}
