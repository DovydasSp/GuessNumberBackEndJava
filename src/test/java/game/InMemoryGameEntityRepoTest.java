package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryGameEntityRepoTest {
    GameEntityRepository gameRepo;
    Map<Integer, GameEntity> inMemoryDb;
    GameEntity gameEntity;

    @BeforeEach
    void setUp() {
        inMemoryDb = new HashMap<Integer, GameEntity>() {
        };
        gameEntity = new GameEntity(1, 2, 3);
        inMemoryDb.put(gameEntity.returnGameId(), gameEntity);
        gameRepo = new InMemoryGameEntityRepo(inMemoryDb);
    }

    @Test
    void getEntity() {
        assertEquals(gameRepo.getEntity(gameEntity.returnGameId()), gameEntity);
    }

    @Test
    void save() {
        GameEntity newGameEntity = new GameEntity(gameEntity.returnGameId(), 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.getEntity(newGameEntity.returnGameId()), newGameEntity);
        newGameEntity = new GameEntity(3, 3, 4);
        gameRepo.save(newGameEntity);
        assertEquals(gameRepo.getEntity(newGameEntity.returnGameId()), newGameEntity);
    }


}
