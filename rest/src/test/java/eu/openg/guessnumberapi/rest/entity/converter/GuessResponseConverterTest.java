package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessResponseConverterTest {
    private GuessResponseConverter guessResponseConverter;

    @BeforeEach
    void setUp() {
        guessResponseConverter = new GuessResponseConverter();
    }

    @Test
    void convertCorrectGuessResponse() {
        convertGuessResponse(null, 10);
    }

    @Test
    void convertHigherGuessResponse() {
        convertGuessResponse(BoundaryGuessResultStatus.LESS, null);
    }

    @Test
    void convertLowerGuessResponse() {
        convertGuessResponse(BoundaryGuessResultStatus.MORE, null);
    }

    private void convertGuessResponse(BoundaryGuessResultStatus status, Integer guess_count) {
        BoundaryGuessResponse guessResponse = new BoundaryGuessResponse(status, guess_count);
        assertEquals(guess_count, guessResponseConverter.convert(guessResponse).getNumberOfGuesses());
        assertEquals(status.toString(),
                guessResponseConverter.convert(guessResponse).getMessage());
    }
}
