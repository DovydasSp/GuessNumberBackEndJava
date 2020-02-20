package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessNumberInteractorTest {
    @Mock
    private GuessValidator gateway;
    @Mock
    private GameEntityRepository gameEntityRepository;

    private GuessNumberUseCase guessNumberInteractor;

    @BeforeEach
    void setUp() {
        guessNumberInteractor = new GuessNumberInteractor(gateway, gameEntityRepository);
    }

    @Test
    void checkLowerGuessAndGuessResponse() {
        int guessedNumber = 1;
        when(gateway.isGuessBiggerThanGenerated(guessedNumber, 3)).thenReturn(false);
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(guessedNumber, false
        );
        verify(gateway).isGuessBiggerThanGenerated(guessedNumber, 3);
        assertEquals(response.getStatus(), BoundaryGuessResultStatus.MORE);
        assertNull(response.getNumberOfGuesses());
    }

    @Test
    void checkHigherGuessAndGuessResponse() {
        int guessedNumber = 5;
        when(gateway.isGuessBiggerThanGenerated(guessedNumber, 3)).thenReturn(true);
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(guessedNumber, false
        );
        verify(gateway).isGuessBiggerThanGenerated(guessedNumber, 3);
        assertEquals(response.getStatus(), BoundaryGuessResultStatus.LESS);
        assertNull(response.getNumberOfGuesses());
    }

    @Test
    void checkCorrectGuessAndGuessResponse() {
        BoundaryGuessResponse response = checkGuessAndReturnGuessResponse(3, true
        );
        assertEquals(response.getNumberOfGuesses(), 1);
        assertNull(response.getStatus());
    }

    private BoundaryGuessResponse checkGuessAndReturnGuessResponse(int guessedNumber, boolean isCorrect) {
        mockAndInitializeRepositoryAndGateway(guessedNumber, isCorrect);

        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(1, guessedNumber);

        verifyCallsHaveBeenMadeAndCaptureGameEntity(guessedNumber);
        return response;
    }

    private void mockAndInitializeRepositoryAndGateway(int guessedNumber, boolean isCorrect) {
        GameEntity gameEntity = new GameEntity(1, 0, 3);
        when(gameEntityRepository.fetchGameEntity(1)).thenReturn(gameEntity);

        when(gateway.isGuessCorrect(guessedNumber, 3)).thenReturn(isCorrect);
    }

    private void verifyCallsHaveBeenMadeAndCaptureGameEntity(int guessedNumber) {
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        verify(gameEntityRepository).fetchGameEntity(1);
        verify(gateway).isGuessCorrect(guessedNumber, 3);

        assertReturnedAnswers(captor);
    }

    private void assertReturnedAnswers(ArgumentCaptor<GameEntity> captor) {
        assertEquals(captor.getValue().getGameId(), 1);
        assertEquals(captor.getValue().getGeneratedNumber(), 3);
    }

}
