package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGameInteractorTest {
    @Mock
    private NumberGateway gateway;
    @Mock
    private GameEntityRepository gameEntityRepository;
    private CreateGameUseCase createGameInteractor;

    @BeforeEach
    void setUp() {
        createGameInteractor = new CreateGameInteractor(gateway, gameEntityRepository);
    }

    @Test
    void generateIdAndNumberThenSave() {
        when(gateway.generateNumber()).thenReturn(123);
        when(gameEntityRepository.save(any())).thenReturn(2);
        int id = createGameInteractor.createGameAndReturnGameId();
        verifyGeneratingAndSaving(id);
    }

    private void verifyGeneratingAndSaving(int id) {
        assertEquals(2, id);
        verify(gateway).generateNumber();
        ArgumentCaptor<GameEntity> captor = ArgumentCaptor.forClass(GameEntity.class);
        verify(gameEntityRepository).save(captor.capture());
        assertEquals(captor.getValue().getGuessCount(), 0);
        assertEquals(captor.getValue().getGeneratedNumber(), 123);
    }
}
