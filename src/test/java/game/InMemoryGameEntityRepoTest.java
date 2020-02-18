package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryGameEntityRepoTest {
    private GameEntityRepository gameRepo;
    private GameEntity gameEntity;

    @BeforeEach
    void setUp() {
        Map<Integer, GameEntity> inMemoryDb = new HashMap<>();
        gameEntity = new GameEntity(1, 2, 3);
        inMemoryDb.put(gameEntity.returnGameId(), gameEntity);
        gameRepo = new InMemoryGameEntityRepo(inMemoryDb);
    }

    @Test
    void fetchGameEntityReturnsFromRepository() {
        assertEquals(gameRepo.fetchGameEntity(gameEntity.returnGameId()), gameEntity);
    }

    @Test
    void saveNewEntityWithSameId() {
        GameEntity newGameEntity = new GameEntity(gameEntity.returnGameId(), 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.fetchGameEntity(newGameEntity.returnGameId()), newGameEntity);
    }

    @Test
    void saveNewEntityWithNewId() {
        GameEntity newGameEntity = new GameEntity(3, 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.fetchGameEntity(newGameEntity.returnGameId()), newGameEntity);
    }

    @Test
    void savingNullDoesNotThrowException() {
        assertDoesNotThrow(() -> gameRepo.save(null));
    }
}
