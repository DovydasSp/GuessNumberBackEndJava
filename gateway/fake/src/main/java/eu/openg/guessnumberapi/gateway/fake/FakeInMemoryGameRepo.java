package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;

import java.util.ArrayList;
import java.util.List;

public class FakeInMemoryGameRepo implements GameRepository {
    public static final int GAME_ID = 1;

    @Override
    public int saveNewGameAndReturnId(Game game) {
        return GAME_ID;
    }

    @Override
    public int incrementThenReturnGuessCount(int gameId) {
        return 2;
    }

    @Override
    public Game fetchGame(int gameId) {
        return new Game(GAME_ID, 1, 2);
    }

    @Override
    public List<Game> fetchGames() {
        List<Game> storage = new ArrayList<>();
        storage.add(new Game(11, 1, 11));
        storage.add(new Game(22, 2, 22));
        return storage;
    }
}
