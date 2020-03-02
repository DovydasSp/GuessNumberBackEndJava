package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;
import eu.openg.guessnumberapi.usecase.implementation.GetGamesInteractor;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GetGamesIntegrationTest {
    private GetGamesUseCase getGamesUseCase;
    private GameRepository gameRepository;

    @Test
    void getGamesWhenDbIsEmpty() {
        gameRepository = new InMemoryGameRepo(new FakeGameIdProvider());
        getGamesUseCase = new GetGamesInteractor(gameRepository);

        List<BoundaryGame> boundaryGames = getGamesUseCase.fetchGames();
        assertEquals(Collections.emptyList(), boundaryGames);
    }

    @Test
    void getGamesWhenDbIsNotEmpty() {
        List<BoundaryGame> expectedBoundaryGames = asList(
                new BoundaryGame(22, 2, 22),
                new BoundaryGame(11, 1, 11));

        Map<Integer, Game> storage = new HashMap<>();
        storage.put(22, new Game(22, 2, 22));
        storage.put(11, new Game(11, 1, 11));


        gameRepository = new InMemoryGameRepo(storage, new FakeGameIdProvider());
        getGamesUseCase = new GetGamesInteractor(gameRepository);

        List<BoundaryGame> actualBoundaryGames = getGamesUseCase.fetchGames();

        assertEquals(expectedBoundaryGames.get(0).getActualNumber(), actualBoundaryGames.get(0).getActualNumber());
        assertEquals(expectedBoundaryGames.get(1).getActualNumber(), actualBoundaryGames.get(1).getActualNumber());
        assertEquals(expectedBoundaryGames.get(0).getGameId(), actualBoundaryGames.get(0).getGameId());
        assertEquals(expectedBoundaryGames.get(1).getGameId(), actualBoundaryGames.get(1).getGameId());
        assertEquals(expectedBoundaryGames.get(0).getGuessCount(), actualBoundaryGames.get(0).getGuessCount());
        assertEquals(expectedBoundaryGames.get(1).getGuessCount(), actualBoundaryGames.get(1).getGuessCount());
    }
}
