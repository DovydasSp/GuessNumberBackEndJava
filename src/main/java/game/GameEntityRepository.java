package game;

public interface GameEntityRepository {
    void save(GameEntity gameEntity);

    GameEntity fetchGameEntity(int gameId);
}
