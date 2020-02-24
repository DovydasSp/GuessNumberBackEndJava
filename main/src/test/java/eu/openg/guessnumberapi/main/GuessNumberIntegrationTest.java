package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameEntityRepo;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.implementation.GuessNumberInteractor;
import eu.openg.guessnumberapi.usecase.implementation.GuessValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GuessNumberIntegrationTest {
    private GuessNumberUseCase guessNumberUseCase;
    private GameEntityRepository gameEntityRepository;

    @BeforeEach
    void setUp() {
        GuessValidator gateway = new GuessValidator();
        gameEntityRepository = new FakeInMemoryGameEntityRepo(new FakeGameIdProvider());
        guessNumberUseCase = new GuessNumberInteractor(gateway, gameEntityRepository);
    }

    @Test
    void checkLowerGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(1, BoundaryGuessResultStatus.MORE, null);
    }

    @Test
    void checkHigherGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(5, BoundaryGuessResultStatus.LESS, null);
    }

    @Test
    void checkCorrectGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(3, null, 2);
    }

    private void checkGuessAndReturnedResponse(int guessNumber, BoundaryGuessResultStatus expectedStatus,
                                               Integer expectedNumberOfGuesses) {
        BoundaryGuessResponse guessResponse = guessNumberUseCase.checkGuessAndReturnResponse(1, guessNumber);
        assertEquals(expectedStatus, guessResponse.getStatus());
        assertEquals(expectedNumberOfGuesses, guessResponse.getNumberOfGuesses());
    }
}
