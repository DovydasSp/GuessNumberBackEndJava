package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGame;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
    void routeReceivesGameIdNullAndCallsSerializerWithIt() {
        String message = "{\"message\":\"Serialization failed.\"}";
        when(serializer.serialize(any())).thenReturn(of(message));
        when(generateNumberUseCase.createGameAndReturnGameId()).thenReturn(0);
        postGameRoute.handle(request, response);
        verify(response).body(message);
    }

    private void routeReceivesGameIdAndCallsSerializerWithIt(int gameId) {
        when(generateNumberUseCase.createGameAndReturnGameId()).thenReturn(gameId);
        mockSerializer(gameId);
        postGameRoute.handle(request, response);
        verifyCalls("\"gameId\":" + gameId + "");
    }

    private void mockSerializer(int gameId) {
        when(serializer.serialize(any(RestGame.class))).thenReturn(Optional.of("\"gameId\":" + gameId));
    }

    private void verifyCalls(String body) {
        verify(generateNumberUseCase).createGameAndReturnGameId();
        verify(response).body(body);
    }
}
