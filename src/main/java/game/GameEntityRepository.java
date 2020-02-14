package game;

public interface GameEntityRepository {
    void save(GameEntity gameEntity);

    GameEntity getEntity(int gameId);
}
