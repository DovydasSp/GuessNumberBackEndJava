package eu.openg.guessnumberapi.gateway.api;

import eu.openg.guessnumberapi.domain.Game;

public interface GameRepository {
    int saveNewGameAndReturnId(Game game);

    int incrementAndReturnGuessCount(int gameId);

    Game fetchGame(int gameId);

    void closeConnection();
}
