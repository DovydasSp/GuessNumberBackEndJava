package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.implementation.exception.NullGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryRestGameRepoTest {
    private GameRepository gameRepo;
    private Game game;
    @Mock
    private GameIdProvider gameIdProvider;

    @BeforeEach
    void setUp() {
        Map<Integer, Game> inMemoryDb = new HashMap<>();
        game = new Game(1, 2, 3);
        inMemoryDb.put(game.getGameId(), game);
        gameRepo = new InMemoryGameRepo(inMemoryDb, gameIdProvider);
    }

    @Test
    void fetchGameReturnsFromRepository() {
        assertEquals(gameRepo.fetchGame(game.getGameId()), game);
    }

    @Test
    void saveNewEntityWithNewId() {
        when(gameIdProvider.getNextId()).thenReturn(100);
        Game newGame = new Game(0, 3, 4);
        int newId = gameRepo.saveNewGameAndReturnId(newGame);
        Game fetchedGame = gameRepo.fetchGame(newId);
        assertEquals(newGame.getActualNumber(), fetchedGame.getActualNumber());
        assertEquals(newGame.getGuessCount(), fetchedGame.getGuessCount());
    }

    @Test
    void incrementGuessCount() {
        int oldGuessCount = game.getGuessCount();
        int newGuessCount = gameRepo.incrementThenReturnGuessCount(game.getGameId());
        assertEquals(newGuessCount, gameRepo.fetchGame(game.getGameId()).getGuessCount());
        assertEquals(oldGuessCount + 1, newGuessCount);
    }

    @Test
    void savingNullDoesNotThrowException() {
        assertThrows(NullGameException.class, () -> gameRepo.saveNewGameAndReturnId(null));
    }
}
