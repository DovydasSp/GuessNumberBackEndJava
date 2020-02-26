package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessResponseConverterTest {
    private GuessResponseConverter guessResponseConverter;
    private static final int GUESS_COUNT = 10;

    @BeforeEach
    void setUp() {
        guessResponseConverter = new GuessResponseConverter();
    }

    @Test
    void convertCorrectGuessResponse() {
        convertGuessResponse(BoundaryGuessResultStatus.CORRECT, null, GUESS_COUNT);
    }

    @Test
    void convertHigherGuessResponse() {
        convertGuessResponse(BoundaryGuessResultStatus.LESS, BoundaryGuessResultStatus.LESS.toString(), null);
    }

    @Test
    void convertLowerGuessResponse() {
        convertGuessResponse(BoundaryGuessResultStatus.MORE, BoundaryGuessResultStatus.MORE.toString(), null);
    }

    private void convertGuessResponse(BoundaryGuessResultStatus status, String statusString, Integer guess_count) {
        BoundaryGuessResponse guessResponse = new BoundaryGuessResponse(status, GUESS_COUNT);
        assertEquals(guess_count, guessResponseConverter.convert(guessResponse).getNumberOfGuesses());
        assertEquals(statusString, guessResponseConverter.convert(guessResponse).getMessage());
    }
}
