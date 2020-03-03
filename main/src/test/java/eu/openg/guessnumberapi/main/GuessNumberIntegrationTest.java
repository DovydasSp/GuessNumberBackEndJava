package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameRepo;
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
    private GameRepository gameRepository;
    private int id;

    @BeforeEach
    void setUp() {
        GuessValidator gateway = new GuessValidator();
        gameRepository = new FakeInMemoryGameRepo(new FakeGameIdProvider());
        id = gameRepository.saveNewGameAndReturnId(new Game(1, 1, 3));
        guessNumberUseCase = new GuessNumberInteractor(gateway, gameRepository);
    }

    @Test
    void checkLowerGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(1, BoundaryGuessResultStatus.MORE, 2);
    }

    @Test
    void checkHigherGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(3, BoundaryGuessResultStatus.LESS, 2);
    }

    @Test
    void checkCorrectGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(2, BoundaryGuessResultStatus.CORRECT, 2);
    }

    private void checkGuessAndReturnedResponse(int guessNumber, BoundaryGuessResultStatus expectedStatus,
                                               Integer expectedNumberOfGuesses) {
        BoundaryGuessResponse guessResponse = guessNumberUseCase.checkGuessAndReturnResponse(id, guessNumber);
        assertEquals(expectedStatus, guessResponse.getStatus());
        assertEquals(expectedNumberOfGuesses, guessResponse.getNumberOfGuesses());
    }
}
