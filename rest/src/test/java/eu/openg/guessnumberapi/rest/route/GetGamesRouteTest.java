package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGame;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.rest.exception.ServerErrorException;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetGamesRouteTest {
    private static GetGamesRoute getGamesRoute;
    @Mock
    private static UseCaseFactory useCaseFactory;
    @Mock
    private static JSONSerializer serializer;
    @Mock
    private static GetGamesUseCase getGamesUseCase;
    @Mock
    private static Request request;
    @Mock
    private static Response response;
    @Mock
    private static RestResponseConverter restResponseConverter;
    private List<BoundaryGame> boundaryGames = new ArrayList<>();
    private List<RestGame> restGames = new ArrayList<>();
    private String responseBody = "";

    @BeforeEach
    void setUp() {
        getGamesRoute = new GetGamesRoute(useCaseFactory, serializer, restResponseConverter);
    }

    @Test
    void returnGamesWhenDbIsNotEmpty() {
        int gamesCount = 4;
        assignResponseBody(gamesCount);
        assignRestGames(gamesCount);
        assignBoundaryGames(gamesCount);
        createMocks();
        getGamesRoute.handle(request, response);
        verify(response).body(responseBody);
    }

    @Test
    void returnGamesWhenDbIsEmpty() {
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        assertThrows(ServerErrorException.class, () -> getGamesRoute.handle(request, response));
    }

    private void assignBoundaryGames(int gamesCount) {
        IntStream.rangeClosed(1, gamesCount).forEach(i -> boundaryGames.add(new BoundaryGame(i, i, i)));
    }

    private void assignRestGames(int gamesCount) {
        IntStream.rangeClosed(1, gamesCount).forEach(i -> restGames.add(new RestGame(i, i, i)));
    }

    private void assignResponseBody(int gamesCount) {
        IntStream.rangeClosed(1, gamesCount)
                .forEach(i -> responseBody += "{\"gameId\":" + i + ",\"guessCount\":" + i + ",\"actualNumber\":" + i + "},");
        responseBody = responseBody.substring(0, responseBody.lastIndexOf(','));
    }

    private void createMocks() {
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        when(getGamesUseCase.fetchGames()).thenReturn(boundaryGames);
        when(serializer.serialize(anyList())).thenReturn(Optional.of(responseBody));
        when(restResponseConverter.convert(anyList())).thenReturn(restGames);
    }
}
