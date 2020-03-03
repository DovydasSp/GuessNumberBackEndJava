package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameRepo;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;
import eu.openg.guessnumberapi.usecase.implementation.GetGamesInteractor;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class GetGamesIntegrationTest {
    @Test
    void getGamesWhenDbIsEmpty() {
        GameRepository gameRepository = new InMemoryGameRepo(new FakeGameIdProvider());
        GetGamesUseCase getGamesUseCase = new GetGamesInteractor(gameRepository);

        List<BoundaryGame> boundaryGames = getGamesUseCase.fetchGames();
        assertThat(boundaryGames).isEmpty();
    }

    @Test
    void getGamesWhenDbIsNotEmpty() {
        Map<Integer, Game> storage = new HashMap<>();
        storage.put(22, new Game(22, 2, 22));
        storage.put(11, new Game(11, 1, 11));

        GameRepository gameRepository = new FakeInMemoryGameRepo();
        GetGamesUseCase getGamesUseCase = new GetGamesInteractor(gameRepository);

        List<BoundaryGame> actualBoundaryGames = getGamesUseCase.fetchGames();

        assertGame(storage.get(11), actualBoundaryGames.get(0));
        assertGame(storage.get(22), actualBoundaryGames.get(1));
    }

    void assertGame(Game expected, BoundaryGame actual) {
        assertThat(expected.getActualNumber()).isEqualTo(actual.getActualNumber());
        assertThat(expected.getGameId()).isEqualTo(actual.getGameId());
        assertThat(expected.getGuessCount()).isEqualTo(actual.getGuessCount());
    }
}
