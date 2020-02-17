import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateGameRouteTest {
    private CreateGameRoute postGameRoute;
    @Mock
    private UseCaseFactory useCaseFactory;
    @Mock
    private JSONSerializer serializer;
    @Mock
    private CreateGameUseCase generateNumberUseCase;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @BeforeEach
    void setUp() {
        when(useCaseFactory.buildCreateGameUseCase()).thenReturn(generateNumberUseCase);
        postGameRoute = new CreateGameRoute(useCaseFactory, serializer);
    }

    @Test
    void routeReceivesGameId8AndCallsSerializerWithIt() {
        routeReceivesGameIdAndCallsSerializerWithIt(8);
    }

    @Test
    void routeReceivesGameId78578AndCallsSerializerWithIt() {
        routeReceivesGameIdAndCallsSerializerWithIt(78578);
    }

    private void routeReceivesGameIdAndCallsSerializerWithIt(int gameId) {
        when(generateNumberUseCase.createGameAndReturnGameId()).thenReturn(gameId);
        mockSerializer(gameId);
        postGameRoute.handle(request, response);
        verifyCalls(gameId);
    }

    private void mockSerializer(int gameId) {
        when(serializer.serialize(createResponseMap(gameId))).thenReturn(Optional.of("\"gameId\":" + gameId));
    }

    private void verifyCalls(int gameId) {
        verify(generateNumberUseCase).createGameAndReturnGameId();
        verify(serializer).serialize(createResponseMap(gameId));
        verify(response).body("\"gameId\":" + gameId + "");
    }

    private Map<String, Integer> createResponseMap(int gameId) {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", gameId);
        return values;
    }

    @Test
    void routeReceivesGameIdNullAndCallsSerializerWithIt() {
        int gameId = 0;
        when(generateNumberUseCase.createGameAndReturnGameId()).thenReturn(gameId);
        when(serializer.serialize(createResponseMap(gameId))).thenReturn(Optional.empty());
        postGameRoute.handle(request, response);
        verify(generateNumberUseCase).createGameAndReturnGameId();
        verify(serializer).serialize(createResponseMap(gameId));
        verify(response).body("");
    }
}
