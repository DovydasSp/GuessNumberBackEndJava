package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> gameIdToGameEntityMap;
    private final GameIdProvider gameIdProvider;

    public InMemoryGameEntityRepo(GameIdProvider gameIdProvider) {
        this(new HashMap<>(), gameIdProvider);
    }

    public InMemoryGameEntityRepo(Map<Integer, GameEntity> storage, GameIdProvider gameIdProvider) {
        gameIdToGameEntityMap = storage;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int save(GameEntity restGameEntity) {
        if (nonNull(restGameEntity)) {
            int id = restGameEntity.getGameId();
            if (id == 0)
                id = gameIdProvider.getNextId();
            gameIdToGameEntityMap.put(id, restGameEntity);
            return id;
        }
        return 0;
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return gameIdToGameEntityMap.get(gameId);
    }
}
