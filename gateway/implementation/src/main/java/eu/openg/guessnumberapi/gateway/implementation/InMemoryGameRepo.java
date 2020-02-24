package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class InMemoryGameRepo implements GameRepository {
    private final Map<Integer, Game> gameIdToGameEntityMap;
    private final GameIdProvider gameIdProvider;

    public InMemoryGameRepo(GameIdProvider gameIdProvider) {
        this(new HashMap<>(), gameIdProvider);
    }

    public InMemoryGameRepo(Map<Integer, Game> storage, GameIdProvider gameIdProvider) {
        gameIdToGameEntityMap = storage;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int save(Game game) {
        if (nonNull(game)) {
            int id = game.getGameId();
            if (id == 0) {
                id = gameIdProvider.getNextId();
                game = new Game(id, game.getGuessCount(), game.getGeneratedNumber());
            }
            gameIdToGameEntityMap.put(id, game);
            return id;
        }
        throw new NullPointerException("Game was null");
    }

    @Override
    public int incrementGuessCount(Game game) {
        if (nonNull(game)) {
            int gameId = game.getGameId();
            int newGuessCount = game.getGuessCount() + 1;
            game = new Game(gameId, newGuessCount, game.getGeneratedNumber());
            gameIdToGameEntityMap.put(gameId, game);
            return newGuessCount;
        }
        throw new NullPointerException("Game was null");
    }

    @Override
    public Game fetchGameEntity(int gameId) {
        return gameIdToGameEntityMap.get(gameId);
    }
}
