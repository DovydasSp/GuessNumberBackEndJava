package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
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
    private void checkLowerGuessAndReturnResponse() {
        checkGuessAndReturnResponse(1, 3, false, false,
                1, "Lower");
    }

    @Test
    private void checkHigherGuessAndReturnResponse() {
        checkGuessAndReturnResponse(5, 3, false, true,
                1, "Higher");
    }

    @Test
    private void checkCorrectGuessAndReturnResponse() {
        checkGuessAndReturnResponse(3, 3, true, false,
                1, "Correct");
    }

    private void checkGuessAndReturnResponse(int guessedNumber, int generatedNumber, boolean isCorrect,
                                             boolean isBiggerThanGenerated, int gameId, String message) {
        mockAndInitialize(guessedNumber, generatedNumber, isCorrect, isBiggerThanGenerated, gameId);

        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(gameId, guessedNumber);

        verifyCalls(guessedNumber, generatedNumber, gameId, message, response);
    }

    private void mockAndInitialize(int guessedNumber, int generatedNumber, boolean isCorrect,
                                   boolean isBiggerThanGenerated, int gameId) {
        GameEntity gameEntity = new GameEntity(gameId, 0, generatedNumber);
        when(gameEntityRepository.fetchGameEntity(gameId)).thenReturn(gameEntity);

        when(gateway.isGuessCorrect(guessedNumber, generatedNumber)).thenReturn(isCorrect);
        when(gateway.isGuessBiggerThanGenerated(guessedNumber, generatedNumber)).thenReturn(isBiggerThanGenerated);
    }

    private void verifyCalls(int guessedNumber, int generatedNumber, int gameId, String message, BoundaryGuessResponse response) {
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        assertEquals(captor.getValue().returnGameId(), gameId);
        assertEquals(captor.getValue().returnGeneratedNumber(), generatedNumber);

        verify(gameEntityRepository).fetchGameEntity(gameId);
        verify(gateway).isGuessCorrect(guessedNumber, generatedNumber);
        verify(gateway).isGuessBiggerThanGenerated(guessedNumber, generatedNumber);

        assertEquals(response.getMessage(), message);
    }

}
