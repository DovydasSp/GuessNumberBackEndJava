package eu.openg.guessnumberapi.gateway.api;

import eu.openg.guessnumberapi.domain.Game;

public interface GameRepository {
    int save(Game restGame);

    int incrementGuessCount(Game game);

    Game fetchGameEntity(int gameId);
}
