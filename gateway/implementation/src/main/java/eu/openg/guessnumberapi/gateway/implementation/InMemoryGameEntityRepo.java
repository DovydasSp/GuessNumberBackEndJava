package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameEntityRepo implements GameEntityRepository {
    private final Map<Integer, GameEntity> gameIdToGameEntityMap;

    public InMemoryGameEntityRepo() {
        this(new HashMap<>());
    }

    public InMemoryGameEntityRepo(Map<Integer, GameEntity> storage) {
        gameIdToGameEntityMap = storage;
    }

    @Override
    public void save(GameEntity restGameEntity) {
        if (nonNull(restGameEntity))
            gameIdToGameEntityMap.put(restGameEntity.returnGameId(), restGameEntity);
    }

    @Override
    public GameEntity fetchGameEntity(int gameId) {
        return gameIdToGameEntityMap.get(gameId);
    }
}
