package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
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
class CreateGameInteractorTest {
    @Mock
    private NumberGateway gateway;
    @Mock
    private GameEntityRepository gameEntityRepository;
    @Mock
    private GameIdProvider gameIdProvider;
    private CreateGameUseCase createGameInteractor;

    @BeforeEach
    void setUp() {
        createGameInteractor = new CreateGameInteractor(gateway, gameEntityRepository, gameIdProvider);
    }

    @Test
    void generateIdAndNumberThenSave() {
        when(gameIdProvider.getNextId()).thenReturn(2);
        when(gateway.generateNumber()).thenReturn(123);
        int id = createGameInteractor.createGameAndReturnGameId();
        verifyGeneratingAndSaving(id);
    }

    private void verifyGeneratingAndSaving(int id) {
        assertEquals(id, 2);
        verify(gameIdProvider).getNextId();
        verify(gateway).generateNumber();
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        assertEquals(captor.getValue().returnGuessCount(), 0);
        assertEquals(captor.getValue().returnGameId(), 2);
        assertEquals(captor.getValue().returnGeneratedNumber(), 123);
    }
}
