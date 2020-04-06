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
        List<RestGame> restGames = gamesResponseConverter.convert(null);
        assertTrue(restGames.isEmpty());
    }

    @Test
    void convertEmptyList() {
        List<RestGame> restGames = gamesResponseConverter.convert(new ArrayList<>());
        assertTrue(restGames.isEmpty());
    }

    @Test
    void convertListWithItems() {
        List<BoundaryGame> boundaryGames = asList(
                new BoundaryGame(11, 1, 11),
                new BoundaryGame(22, 2, 22));

        List<RestGame> actualRestGames = gamesResponseConverter.convert(boundaryGames);

        assertGame(boundaryGames.get(0), actualRestGames.get(0));
        assertGame(boundaryGames.get(1), actualRestGames.get(1));
    }

    void assertGame(BoundaryGame expected, RestGame actual) {
        assertEquals(expected.getActualNumber(), actual.returnActualNumber());
        assertEquals(expected.getGameId(), actual.returnGameId());
        assertEquals(expected.getGuessCount(), actual.returnGuessCount());
    }
}
