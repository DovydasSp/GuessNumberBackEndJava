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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

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
    private static RestResponseConverter<List<BoundaryGame>, List<RestGame>> restResponseConverter;

    @BeforeEach
    void setUp() {
        getGamesRoute = new GetGamesRoute(useCaseFactory, serializer, restResponseConverter);
    }

    @Test
    void returnGamesWhenDbIsNotEmpty() {
        int gamesCount = 4;
        String responseBody = assignResponseBody(gamesCount);
        List<RestGame> restGames = returnRestGames(gamesCount);
        List<BoundaryGame> boundaryGames = returnBoundaryGames(gamesCount);
        initMocks(restGames, boundaryGames, responseBody);
        getGamesRoute.handle(request, response);
        verify(response).body(responseBody);
    }

    @Test
    void returnGamesWhenDbIsEmpty() {
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        assertThrows(ServerErrorException.class, () -> getGamesRoute.handle(request, response));
    }

    private List<BoundaryGame> returnBoundaryGames(int gamesCount) {
        return IntStream.rangeClosed(1, gamesCount)
                .mapToObj(i -> mock(BoundaryGame.class))
                .collect(Collectors.toList());
    }

    private List<RestGame> returnRestGames(int gamesCount) {
        return IntStream.rangeClosed(1, gamesCount)
                .mapToObj(i -> mock(RestGame.class))
                .collect(Collectors.toList());
    }

    private String assignResponseBody(int gamesCount) {
        return IntStream.rangeClosed(1, gamesCount)
                .mapToObj(i -> "{\"gameId\":" + i + ",\"guessCount\":" + i + ",\"actualNumber\":" + i + "}")
                .collect(Collectors.joining(","));
    }

    private void initMocks(List<RestGame> restGames, List<BoundaryGame> boundaryGames, String responseBody) {
        when(useCaseFactory.buildGetGamesUseCase()).thenReturn(getGamesUseCase);
        when(getGamesUseCase.fetchGames()).thenReturn(boundaryGames);
        when(serializer.serialize(anyList())).thenReturn(Optional.of(responseBody));
        when(restResponseConverter.convert(anyList())).thenReturn(restGames);
    }
}
