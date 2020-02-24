package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class FakeInMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> gameIdToGameEntityMap;
    private final GameIdProvider gameIdProvider;

    public FakeInMemoryGameEntityRepo(GameIdProvider gameIdProvider) {
        this(new HashMap<>(), gameIdProvider);
    }

    private FakeInMemoryGameEntityRepo(Map<Integer, GameEntity> storage, GameIdProvider gameIdProvider) {
        gameIdToGameEntityMap = storage;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int save(GameEntity restGameEntity) {
        if (nonNull(restGameEntity)) {
            int id = gameIdProvider.getNextId();
            gameIdToGameEntityMap.put(id, restGameEntity);
            return id;
        }
        return 0;
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return new GameEntity(gameId, 1, 3);
    }
}

