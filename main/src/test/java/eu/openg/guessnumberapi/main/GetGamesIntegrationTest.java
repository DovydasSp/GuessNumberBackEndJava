package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameRepo;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;
import eu.openg.guessnumberapi.usecase.implementation.GetGamesInteractor;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        GameRepository gameRepository = new FakeInMemoryGameRepo();
        GetGamesUseCase getGamesUseCase = new GetGamesInteractor(gameRepository);

        List<BoundaryGame> actualBoundaryGames = getGamesUseCase.fetchGames();

        assertGame(11, 1, 11, actualBoundaryGames.get(0));
        assertGame(22, 2, 22, actualBoundaryGames.get(1));
    }

    void assertGame(int gameId, int guessCount, int actualNumber, BoundaryGame actual) {
        assertThat(actualNumber).isEqualTo(actual.getActualNumber());
        assertThat(gameId).isEqualTo(actual.getGameId());
        assertThat(guessCount).isEqualTo(actual.getGuessCount());
    }
}
