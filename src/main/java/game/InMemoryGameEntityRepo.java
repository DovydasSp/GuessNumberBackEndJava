package game;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> inMemoryStorage;

    public InMemoryGameEntityRepo() {
        this(new HashMap<>());
    }

    public InMemoryGameEntityRepo(Map<Integer, GameEntity> storage) {
        inMemoryStorage = storage;
    }

    @Override
    public void save(GameEntity gameEntity) {
        if (nonNull(gameEntity))
            inMemoryStorage.put(gameEntity.returnGameId(), gameEntity);
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return inMemoryStorage.get(gameId);
    }
}
