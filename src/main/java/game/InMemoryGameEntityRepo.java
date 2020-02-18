package game;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> gameEntityToGameEntityMap;

    public InMemoryGameEntityRepo() {
        this(new HashMap<>());
    }

    public InMemoryGameEntityRepo(Map<Integer, GameEntity> storage) {
        gameEntityToGameEntityMap = storage;
    }

    @Override
    public void save(GameEntity gameEntity) {
        if (nonNull(gameEntity))
            gameEntityToGameEntityMap.put(gameEntity.returnGameId(), gameEntity);
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return gameEntityToGameEntityMap.get(gameId);
    }
}
