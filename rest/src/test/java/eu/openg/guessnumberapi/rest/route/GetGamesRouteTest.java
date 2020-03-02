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

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
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
    private List<BoundaryGame> boundaryGames;
    private List<RestGame> restGames;
    private String responseBody;

    @BeforeEach
    void setUp() {
        getGamesRoute = new GetGamesRoute(useCaseFactory, serializer, restResponseConverter);
    }

    @Test
    void returnGamesWhenDbIsNotEmpty() {
        assignVariables();
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        when(getGamesUseCase.fetchGames()).thenReturn(boundaryGames);
        when(serializer.serialize(anyList())).thenReturn(Optional.of(responseBody));
        when(restResponseConverter.convert(anyList())).thenReturn(restGames);
        getGamesRoute.handle(request, response);
        verify(response).body(responseBody);
    }

    @Test
    void returnGamesWhenDbIsEmpty() {
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        when(getGamesUseCase.fetchGames()).thenReturn(null);
        assertThrows(ServerErrorException.class, () -> getGamesRoute.handle(request, response));
    }

    private void assignVariables() {
        boundaryGames = asList(
                new BoundaryGame(11, 1, 11),
                new BoundaryGame(22, 2, 22),
                new BoundaryGame(33, 3, 33),
                new BoundaryGame(44, 4, 44));

        restGames = asList(
                new RestGame(11, 1, 11),
                new RestGame(22, 2, 22),
                new RestGame(33, 3, 33),
                new RestGame(44, 4, 44));

        responseBody = "{\"gameId\":11,\"guessCount\":1,\"actualNumber\":11}," +
                "{\"gameId\":22,\"guessCount\":2,\"actualNumber\":22}," +
                "{\"gameId\":33,\"guessCount\":3,\"actualNumber\":33}," +
                "{\"gameId\":44,\"guessCount\":4,\"actualNumber\":44}";
    }
}
