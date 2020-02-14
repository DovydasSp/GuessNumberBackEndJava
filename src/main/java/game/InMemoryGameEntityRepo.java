package game;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> inMemoryDb;

    public InMemoryGameEntityRepo() {
        inMemoryDb = new HashMap<>();
    }

    public InMemoryGameEntityRepo(Map<Integer, GameEntity> db) {
        inMemoryDb = db;
    }

    @Override
    public void save(GameEntity gameEntity) {
        inMemoryDb.put(gameEntity.returnGameId(), gameEntity);
    }

    @Override
    public GameEntity getEntity(int gameId) {
        return inMemoryDb.get(gameId);
    }
}
