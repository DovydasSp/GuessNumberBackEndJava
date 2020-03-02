package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
        List<Game> returnedGames = new ArrayList<>();
        when(gameRepository.fetchGames()).thenReturn(returnedGames);
        List<BoundaryGame> boundaryGames = interactor.fetchGames();
        assertEquals(Collections.emptyList(), boundaryGames);
    }

    @Test
    void fetchFromNonEmptyDb() {
        List<Game> games = asList(
                new Game(11, 1, 11),
                new Game(22, 2, 22));

        List<BoundaryGame> expectedBoundaryGames = asList(
                new BoundaryGame(11, 1, 11),
                new BoundaryGame(22, 2, 22));

        when(gameRepository.fetchGames()).thenReturn(games);

        List<BoundaryGame> actualBoundaryGames = interactor.fetchGames();

        assertEquals(expectedBoundaryGames.get(0).getActualNumber(), actualBoundaryGames.get(0).getActualNumber());
        assertEquals(expectedBoundaryGames.get(1).getActualNumber(), actualBoundaryGames.get(1).getActualNumber());
        assertEquals(expectedBoundaryGames.get(0).getGameId(), actualBoundaryGames.get(0).getGameId());
        assertEquals(expectedBoundaryGames.get(1).getGameId(), actualBoundaryGames.get(1).getGameId());
        assertEquals(expectedBoundaryGames.get(0).getGuessCount(), actualBoundaryGames.get(0).getGuessCount());
        assertEquals(expectedBoundaryGames.get(1).getGuessCount(), actualBoundaryGames.get(1).getGuessCount());
    }
}
