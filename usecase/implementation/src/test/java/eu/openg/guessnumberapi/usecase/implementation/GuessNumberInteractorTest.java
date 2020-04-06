package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @BeforeEach
    void setUp() {
        guessNumberInteractor = new GuessNumberInteractor(gateway, gameRepository);
        createEntityAndMockRepository();
    }

    private void createEntityAndMockRepository() {
        Game game = new Game(1, 0, 3);
        when(gameRepository.fetchGame(1)).thenReturn(game);
        when(gameRepository.incrementThenReturnGuessCount(any(int.class))).thenReturn(1);
    }

    @Test
    void checkLowerGuessAndGuessResponse() {
        checkIncorrectGuessAndGuessResponse(1);
    }

    @Test
    void checkHigherGuessAndGuessResponse() {
        checkIncorrectGuessAndGuessResponse(5);
    }

    @Test
    void checkCorrectGuessAndGuessResponse() {
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(3);
        assertEquals(response.getNumberOfGuesses(), 1);
        assertNull(response.getStatus());
    }

    private void checkIncorrectGuessAndGuessResponse(int guessedNumber) {
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(guessedNumber);
        verifyAndAssertResponse(guessedNumber, response);
    }

    private void verifyAndAssertResponse(int guessedNumber, BoundaryGuessResponse response) {
        verify(gateway).checkGuessAndReturnBoundaryGuessResponse(guessedNumber, 3);
        assertEquals(1, response.getNumberOfGuesses());
    }

    private BoundaryGuessResponse checkGuessAndReturnGuessResponse(int guessedNumber) {
        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(1, guessedNumber);

        verifyCallsHaveBeenMadeAndCaptureGame(guessedNumber);
        return response;
    }

    private void verifyCallsHaveBeenMadeAndCaptureGame(int guessedNumber) {
        verify(gameRepository).incrementThenReturnGuessCount(1);
        verify(gameRepository).fetchGame(1);
        verify(gateway).checkGuessAndReturnBoundaryGuessResponse(guessedNumber, 3);
    }

}
