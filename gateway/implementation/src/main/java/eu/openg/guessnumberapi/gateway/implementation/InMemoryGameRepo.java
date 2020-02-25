package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.implementation.exception.NullGameException;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameRepo implements GameRepository {
    private final Map<Integer, Game> gameIdToGameMap;
    private final GameIdProvider gameIdProvider;

    public InMemoryGameRepo(GameIdProvider gameIdProvider) {
        this(new HashMap<>(), gameIdProvider);
    }

    public InMemoryGameRepo(Map<Integer, Game> storage, GameIdProvider gameIdProvider) {
        gameIdToGameMap = storage;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int saveNewGameAndReturnId(Game game) {
        if (nonNull(game)) {
            int id = gameIdProvider.getNextId();
            Game newGame = new Game(id, game.getGuessCount(), game.getActualNumber());
            gameIdToGameMap.put(id, newGame);
            return id;
        }
        throw new NullGameException("Game was null");
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        Game game = gameIdToGameMap.get(gameId);
        int newGuessCount = game.getGuessCount() + 1;
        Game newGame = new Game(gameId, newGuessCount, game.getActualNumber());
        gameIdToGameMap.put(gameId, newGame);
        return newGuessCount;
    }

    @Override
    public Game fetchGame(int gameId) {
        return gameIdToGameMap.get(gameId);
    }
}
