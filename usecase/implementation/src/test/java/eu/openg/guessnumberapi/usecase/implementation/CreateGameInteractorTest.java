package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
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
    private GameRepository gameRepository;
    private CreateGameUseCase createGameInteractor;

    @BeforeEach
    void setUp() {
        createGameInteractor = new CreateGameInteractor(gateway, gameRepository);
    }

    @Test
    void generateIdAndNumberThenSave() {
        when(gateway.generateNumber()).thenReturn(123);
        when(gameRepository.saveNewGameAndReturnId(any(Game.class))).thenReturn(2);
        int id = createGameInteractor.createGameAndReturnGameId();
        verifyGeneratingAndSaving(id);
    }

    private void verifyGeneratingAndSaving(int id) {
        assertEquals(2, id);
        verify(gateway).generateNumber();
        ArgumentCaptor<Game> captor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).saveNewGameAndReturnId(captor.capture());
        assertEquals(captor.getValue().getGuessCount(), 0);
        assertEquals(captor.getValue().getActualNumber(), 123);
    }
}
