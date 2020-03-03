package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameIdProvider;
import eu.openg.guessnumberapi.gateway.api.GameRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeInMemoryGameRepo implements GameRepository {
    private final Map<Integer, Game> gameIdToGameMap;
    private final GameIdProvider gameIdProvider;

    public FakeInMemoryGameRepo(GameIdProvider gameIdProvider) {
        this(new HashMap<>(), gameIdProvider);
    }

    public FakeInMemoryGameRepo(Map<Integer, Game> storage, GameIdProvider gameIdProvider) {
        gameIdToGameMap = storage;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int saveNewGameAndReturnId(Game game) {
        return 1;
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        return 2;
    }

    @Override
    public Game fetchGame(int gameId) {
        return new Game(1, 1, 2);
    }

    @Override
    public List<Game> fetchGames() {
        List<Game> storage = new ArrayList<>();
        storage.add(new Game(11, 1, 11));
        storage.add(new Game(22, 2, 22));
        return storage;
    }
}
