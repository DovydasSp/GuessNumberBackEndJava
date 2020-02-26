package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessResponseConverterTest {
    GuessResponseConverter guessResponseConverter;
    private static final int GUESS_COUNT = 10;

    @BeforeEach
    void setUp() {
        guessResponseConverter = new GuessResponseConverter();
    }

    @Test
    void convertCorrectGuessResponse() {
        BoundaryGuessResponse guessResponse = new BoundaryGuessResponse(BoundaryGuessResultStatus.CORRECT, GUESS_COUNT);
        assertEquals(GUESS_COUNT, guessResponseConverter.convert(guessResponse).getNumberOfGuesses());
        assertEquals(null, guessResponseConverter.convert(guessResponse).getMessage());
    }

    @Test
    void convertHigherGuessResponse() {
        BoundaryGuessResponse guessResponse = new BoundaryGuessResponse(BoundaryGuessResultStatus.LESS, GUESS_COUNT);
        assertEquals(null, guessResponseConverter.convert(guessResponse).getNumberOfGuesses());
        assertEquals(BoundaryGuessResultStatus.LESS.toString(),
                guessResponseConverter.convert(guessResponse).getMessage());
    }

    @Test
    void convertLowerGuessResponse() {
        BoundaryGuessResponse guessResponse = new BoundaryGuessResponse(BoundaryGuessResultStatus.MORE, GUESS_COUNT);
        assertEquals(null, guessResponseConverter.convert(guessResponse).getNumberOfGuesses());
        assertEquals(BoundaryGuessResultStatus.MORE.toString(),
                guessResponseConverter.convert(guessResponse).getMessage());
    }
}
