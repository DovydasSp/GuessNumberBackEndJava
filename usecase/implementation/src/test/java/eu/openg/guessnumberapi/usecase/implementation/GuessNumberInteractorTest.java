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
    void checkLowerGuessAndReturnResponse() {
        checkGuessAndReturnResponse(1, false, false,
                "Lower");
    }

    @Test
    void checkHigherGuessAndReturnResponse() {
        checkGuessAndReturnResponse(5, false, true,
                "Higher");
    }

    @Test
    void checkCorrectGuessAndReturnResponse() {
        checkGuessAndReturnResponse(3, true, false,
                "Correct");
    }

    private void checkGuessAndReturnResponse(int guessedNumber, boolean isCorrect,
                                             boolean isBiggerThanGenerated, String message) {
        mockAndInitialize(guessedNumber, isCorrect, isBiggerThanGenerated);

        BoundaryGuessResponse response = guessNumberInteractor.checkGuessAndReturnResponse(1, guessedNumber);

        verifyCalls(guessedNumber, message, response);
    }

    private void mockAndInitialize(int guessedNumber, boolean isCorrect,
                                   boolean isBiggerThanGenerated) {
        GameEntity gameEntity = new GameEntity(1, 0, 3);
        when(gameEntityRepository.fetchGameEntity(1)).thenReturn(gameEntity);

        when(gateway.isGuessCorrect(guessedNumber, 3)).thenReturn(isCorrect);
        if (!isCorrect)
            when(gateway.isGuessBiggerThanGenerated(guessedNumber, 3)).thenReturn(isBiggerThanGenerated);
    }

    private void verifyCalls(int guessedNumber, String message, BoundaryGuessResponse response) {
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        assertEquals(captor.getValue().returnGameId(), 1);
        assertEquals(captor.getValue().returnGeneratedNumber(), 3);

        verify(gameEntityRepository).fetchGameEntity(1);
        verify(gateway).isGuessCorrect(guessedNumber, 3);
        if (!message.equals("Correct"))
            verify(gateway).isGuessBiggerThanGenerated(guessedNumber, 3);

        assertEquals(response.getMessage(), message);
    }

}
