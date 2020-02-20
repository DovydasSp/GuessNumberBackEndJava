package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class FakeInMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> gameIdToGameEntityMap;

    public FakeInMemoryGameEntityRepo() {
        this(new HashMap<>());
    }

    private FakeInMemoryGameEntityRepo(Map<Integer, GameEntity> storage) {
        gameIdToGameEntityMap = storage;
    }

    @Override
    public void save(GameEntity restGameEntity) {
        if (nonNull(restGameEntity))
            gameIdToGameEntityMap.put(restGameEntity.getGameId(), restGameEntity);
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return new GameEntity(gameId, 1, 3);
    }
}

