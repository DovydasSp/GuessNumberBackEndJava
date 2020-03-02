package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGamesInteractorTest {
    @Mock
    private GameRepository gameRepository;
    private GetGamesInteractor interactor;

    @BeforeEach
    void setUp() {
        interactor = new GetGamesInteractor(gameRepository);
    }

    @Test
    void fetchFromEmptyDb() {
        when(gameRepository.fetchGames()).thenReturn(emptyList());
        List<BoundaryGame> boundaryGames = interactor.fetchGames();
        assertThat(boundaryGames).isEmpty();
    }

    @Test
    void fetchFromNonEmptyDb() {
        List<Game> games = asList(
                new Game(11, 1, 11),
                new Game(22, 2, 22));

        when(gameRepository.fetchGames()).thenReturn(games);

        List<BoundaryGame> actualBoundaryGames = interactor.fetchGames();

        assertGame(games.get(0), actualBoundaryGames.get(0));
        assertGame(games.get(1), actualBoundaryGames.get(1));
    }

    void assertGame(Game expected, BoundaryGame actual) {
        assertThat(actual.getActualNumber()).isEqualTo(expected.getActualNumber());
        assertThat(actual.getGameId()).isEqualTo(expected.getGameId());
        assertThat(actual.getGuessCount()).isEqualTo(expected.getGuessCount());
    }
}
