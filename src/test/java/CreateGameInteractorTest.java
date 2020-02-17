import game.GameEntity;
import game.GameEntityRepository;
import game.IIdProvider;
import game.NumberGateway;
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
    private IIdProvider idProvider;
    private CreateGameUseCase createGameInteractor;

    @BeforeEach
    void setUp() {
        createGameInteractor = new CreateGameInteractor(gateway, gameEntityRepository, idProvider);
    }

    @Test
    void execute() {
        when(idProvider.getNextId()).thenReturn(2);
        when(gateway.generateNumber()).thenReturn(123);
        int id = createGameInteractor.createGameAndReturnGameId();
        assertEquals(id, 2);
        verify(idProvider).getNextId();
        verify(gateway).generateNumber();
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
    }
}