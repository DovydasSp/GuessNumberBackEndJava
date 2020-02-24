package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
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
    void fetchGameEntityReturnsFromRepository() {
        assertEquals(gameRepo.fetchGameEntity(game.getGameId()), game);
    }

    @Test
    void saveNewEntityWithSameId() {
        Game newGame = new Game(game.getGameId(), 3, 4);
        gameRepo.save(newGame);
        assertEquals(gameRepo.fetchGameEntity(newGame.getGameId()), newGame);
    }

    @Test
    void saveNewEntityWithNewId() {
        Game newGame = new Game(3, 3, 4);
        gameRepo.save(newGame);
        assertEquals(gameRepo.fetchGameEntity(newGame.getGameId()), newGame);
    }

    @Test
    void saveNewEntityWithId0() {
        when(gameIdProvider.getNextId()).thenReturn(1);
        Game newGame = new Game(0, 3, 4);
        int id = gameRepo.save(newGame);
        Game entity = gameRepo.fetchGameEntity(id);
        assertEquals(entity.getGuessCount(), newGame.getGuessCount());
        assertEquals(entity.getGeneratedNumber(), newGame.getGeneratedNumber());
    }

    @Test
    void savingNullDoesNotThrowException() {
        assertThrows(NullPointerException.class, () -> gameRepo.save(null));
    }
}
