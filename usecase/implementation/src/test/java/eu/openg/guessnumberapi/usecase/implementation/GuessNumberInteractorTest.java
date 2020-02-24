package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessNumberInteractorTest {
    @Mock
    private GuessValidator gateway;
    @Mock
    private GameRepository gameRepository;
    private GuessNumberUseCase guessNumberInteractor;
    private Game game;

    @BeforeEach
    void setUp() {
        guessNumberInteractor = new GuessNumberInteractor(gateway, gameRepository);
        createEntityAndMockRepository();
    }

    private void createEntityAndMockRepository() {
        game = new Game(1, 0, 3);
        when(gameRepository.fetchGameEntity(1)).thenReturn(game);
        when(gameRepository.incrementGuessCount(any(Game.class))).thenReturn(1);
    }

    @Test
    void checkLowerGuessAndGuessResponse() {
        checkIncorrectGuessAndGuessResponse(false, 1, BoundaryGuessResultStatus.MORE);
    }

    @Test
    void checkHigherGuessAndGuessResponse() {
        checkIncorrectGuessAndGuessResponse(true, 5, BoundaryGuessResultStatus.LESS);
    }

    private void checkIncorrectGuessAndGuessResponse(boolean biggerThanGenerated, int guessedNumber,
                                                     BoundaryGuessResultStatus boundaryGuessResultStatus) {
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(guessedNumber, false);
        verifyAndAssertResponse(guessedNumber, response, boundaryGuessResultStatus);
    }

    private void verifyAndAssertResponse(int guessedNumber, BoundaryGuessResponse response,
                                         BoundaryGuessResultStatus boundaryGuessResultStatus) {
        verify(gateway).checkGuessAndReturnBoundaryGuessResponse(guessedNumber, 3);
        assertEquals(1, response.getNumberOfGuesses());
    }

    @Test
    void checkCorrectGuessAndGuessResponse() {
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(3, true);
        assertEquals(response.getNumberOfGuesses(), 1);
        assertNull(response.getStatus());
    }

    private BoundaryGuessResponse checkGuessAndReturnGuessResponse(int guessedNumber, boolean isCorrect) {
        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(1, guessedNumber);

        verifyCallsHaveBeenMadeAndCaptureGameEntity(guessedNumber);
        return response;
    }

    private void verifyCallsHaveBeenMadeAndCaptureGameEntity(int guessedNumber) {
        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).incrementGuessCount(captor.capture());
        verify(gameRepository).fetchGameEntity(1);
        verify(gateway).checkGuessAndReturnBoundaryGuessResponse(guessedNumber, 3);

        assertReturnedAnswers(captor.getValue().getGameId(), captor.getValue().getGeneratedNumber());
    }

    private void assertReturnedAnswers(int gameId, int generatedNumber) {
        assertEquals(1, gameId);
        assertEquals(3, generatedNumber);
    }

}
