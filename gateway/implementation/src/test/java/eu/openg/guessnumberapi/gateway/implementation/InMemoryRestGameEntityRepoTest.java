package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InMemoryRestGameEntityRepoTest {
    private GameEntityRepository gameRepo;
    private GameEntity gameEntity;
    @Mock
    private GameIdProvider gameIdProvider;

    @BeforeEach
    void setUp() {
        Map<Integer, GameEntity> inMemoryDb = new HashMap<>();
        gameEntity = new GameEntity(1, 2, 3);
        inMemoryDb.put(gameEntity.getGameId(), gameEntity);
        gameRepo = new InMemoryGameEntityRepo(inMemoryDb, gameIdProvider);
    }

    @Test
    void fetchGameEntityReturnsFromRepository() {
        assertEquals(gameRepo.fetchGameEntity(gameEntity.getGameId()), gameEntity);
    }

    @Test
    void saveNewEntityWithSameId() {
        GameEntity newGameEntity = new GameEntity(gameEntity.getGameId(), 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.fetchGameEntity(newGameEntity.getGameId()), newGameEntity);
    }

    @Test
    void saveNewEntityWithNewId() {
        GameEntity newGameEntity = new GameEntity(3, 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.fetchGameEntity(newGameEntity.getGameId()), newGameEntity);
    }

    @Test
    void saveNewEntityWithoutId() {
        when(gameIdProvider.getNextId()).thenReturn(1);
        GameEntity newGameEntity = new GameEntity(0, 3, 4);
        int id = gameRepo.save(newGameEntity);
        assertEquals(gameRepo.fetchGameEntity(id).getGuessCount(), newGameEntity.getGuessCount());
        assertEquals(gameRepo.fetchGameEntity(id).getGeneratedNumber(), newGameEntity.getGeneratedNumber());
    }

    @Test
    void savingNullDoesNotThrowException() {
        assertDoesNotThrow(() -> gameRepo.save(null));
    }
}
