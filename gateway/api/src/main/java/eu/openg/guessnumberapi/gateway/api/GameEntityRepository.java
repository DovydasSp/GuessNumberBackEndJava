package eu.openg.guessnumberapi.gateway.api;

import eu.openg.guessnumberapi.domain.GameEntity;

public interface GameEntityRepository {
    void save(GameEntity restGameEntity);

    GameEntity fetchGameEntity(int gameId);
}
