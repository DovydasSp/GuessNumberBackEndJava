package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGame;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamesResponseConverterTest {
    private GamesResponseConverter gamesResponseConverter;

    @BeforeEach
    void setUp() {
        gamesResponseConverter = new GamesResponseConverter();
    }

    @Test
    void convertNullList() {
        List<BoundaryGame> boundaryGames = null;
        List<RestGame> restGames = gamesResponseConverter.convert(boundaryGames);
        assertTrue(restGames.isEmpty());
    }

    @Test
    void convertEmptyList() {
        List<BoundaryGame> boundaryGames = new ArrayList<>();
        List<RestGame> restGames = gamesResponseConverter.convert(boundaryGames);
        assertTrue(restGames.isEmpty());
    }

    @Test
    void convertListWithItems() {
        List<BoundaryGame> boundaryGames = asList(
                new BoundaryGame(11, 1, 11),
                new BoundaryGame(22, 2, 22));

        List<RestGame> expectedRestGames = asList(
                new RestGame(11, 1, 11),
                new RestGame(22, 2, 22));

        List<RestGame> actualRestGames = gamesResponseConverter.convert(boundaryGames);

        assertEquals(expectedRestGames.get(0).returnActualNumber(), actualRestGames.get(0).returnActualNumber());
        assertEquals(expectedRestGames.get(1).returnActualNumber(), actualRestGames.get(1).returnActualNumber());
        assertEquals(expectedRestGames.get(0).returnGameId(), actualRestGames.get(0).returnGameId());
        assertEquals(expectedRestGames.get(1).returnGameId(), actualRestGames.get(1).returnGameId());
        assertEquals(expectedRestGames.get(0).returnGuessCount(), actualRestGames.get(0).returnGuessCount());
        assertEquals(expectedRestGames.get(1).returnGuessCount(), actualRestGames.get(1).returnGuessCount());
    }
}
