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
        boolean isBiggerThanGenerated = false;
        when(gateway.isGuessBiggerThanGenerated(guessedNumber, 3)).thenReturn(isBiggerThanGenerated);
        checkGuessAndGuessResponse(guessedNumber, false, BoundaryGuessResultStatus.LOWER);
        verify(gateway).isGuessBiggerThanGenerated(guessedNumber, 3);
    }

    @Test
    void checkHigherGuessAndGuessResponse() {
        int guessedNumber = 5;
        boolean isBiggerThanGenerated = true;
        when(gateway.isGuessBiggerThanGenerated(guessedNumber, 3)).thenReturn(isBiggerThanGenerated);
        checkGuessAndGuessResponse(guessedNumber, false, BoundaryGuessResultStatus.HIGHER);
        verify(gateway).isGuessBiggerThanGenerated(guessedNumber, 3);
    }

    @Test
    void checkCorrectGuessAndGuessResponse() {
        checkGuessAndGuessResponse(3, true, BoundaryGuessResultStatus.CORRECT);
    }

    private void checkGuessAndGuessResponse(int guessedNumber, boolean isCorrect,
                                            BoundaryGuessResultStatus message) {
        mockAndInitializeRepositoryAndGateway(guessedNumber, isCorrect);

        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(1, guessedNumber);

        verifyCallsHaveBeenMadeAndCaptureGameEntity(guessedNumber, message, response);
    }

    private void mockAndInitializeRepositoryAndGateway(int guessedNumber, boolean isCorrect) {
        GameEntity gameEntity = new GameEntity(1, 0, 3);
        when(gameEntityRepository.fetchGameEntity(1)).thenReturn(gameEntity);

        when(gateway.isGuessCorrect(guessedNumber, 3)).thenReturn(isCorrect);
    }

    private void verifyCallsHaveBeenMadeAndCaptureGameEntity(int guessedNumber, BoundaryGuessResultStatus message, BoundaryGuessResponse response) {
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        verify(gameEntityRepository).fetchGameEntity(1);
        verify(gateway).isGuessCorrect(guessedNumber, 3);

        assertReturnedAnswers(captor, message, response);
    }

    private void assertReturnedAnswers(ArgumentCaptor<GameEntity> captor, BoundaryGuessResultStatus message, BoundaryGuessResponse response) {
        assertEquals(captor.getValue().getGameId(), 1);
        assertEquals(captor.getValue().getGeneratedNumber(), 3);
        assertEquals(response.getStatus(), message);
    }

}
