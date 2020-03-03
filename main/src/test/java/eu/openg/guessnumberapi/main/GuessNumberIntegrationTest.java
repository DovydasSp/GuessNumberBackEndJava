package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
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
    private int id;

    @BeforeEach
    void setUp() {
        GuessValidator gateway = new GuessValidator();
        GameRepository gameRepository = new FakeInMemoryGameRepo();
        id = gameRepository.saveNewGameAndReturnId(new Game(1, 1, 3));
        guessNumberUseCase = new GuessNumberInteractor(gateway, gameRepository);
    }

    @Test
    void checkLowerGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(1, BoundaryGuessResultStatus.MORE);
    }

    @Test
    void checkHigherGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(3, BoundaryGuessResultStatus.LESS);
    }

    @Test
    void checkCorrectGuessAndReturnedResponse() {
        checkGuessAndReturnedResponse(2, BoundaryGuessResultStatus.CORRECT);
    }

    private void checkGuessAndReturnedResponse(int guessNumber, BoundaryGuessResultStatus expectedStatus) {
        BoundaryGuessResponse guessResponse = guessNumberUseCase.checkGuessAndReturnResponse(id, guessNumber);
        assertEquals(expectedStatus, guessResponse.getStatus());
        assertEquals((Integer) 2, guessResponse.getNumberOfGuesses());
    }
}
