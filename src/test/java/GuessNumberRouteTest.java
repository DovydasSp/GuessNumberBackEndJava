import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessNumberRouteTest {

    private static GuessNumberRoute guessNumberRoute;
    @Mock
    private static UseCaseFactory useCaseFactory;
    @Mock
    private static JSONSerializer serializer;
    @Mock
    private static GuessNumberUseCase guessNumberUseCase;
    @Mock
    private static Request request;
    @Mock
    private static Response response;

    @BeforeEach
    void setUp() {
        guessNumberRoute = new GuessNumberRoute(useCaseFactory, serializer);
    }

    @Test
    void routeReceivesGameIdAndCallsCheckerWithIt() throws Exception {
        int gameId = 37;
        int guessNumber = 5;
        Map<String, String> map = new HashMap<>();
        map.put("guessNumber", "5");
        String body = "{\"guessNumber\":\"" + guessNumber + "\"}";

        mockRequest(gameId, body, map);

        guessNumberRoute.handle(request, response);

        verify(request).params("id");
        verify(guessNumberUseCase).checkGuessAndReturnResponse(gameId, guessNumber);
    }

    @Test
    void routeReceivesGameIdNullAndCallsCheckerWithIt() throws Exception {
        String message = "{\"message\":\"Invalid Game ID\"}";
        when(serializer.serialize(ArgumentMatchers.any())).thenReturn(of(message));
        guessNumberRoute.handle(request, response);
        verifyRequestAndResponse(message);
    }

    @Test
    void routeReceivesNullGuessAndCallsCheckerWithIt() throws Exception {
        String message = "{\"message\":\"Guess invalid\"}";
        when(serializer.serialize(ArgumentMatchers.any())).thenReturn(of(message));
        when(request.params("id")).thenReturn("1");
        guessNumberRoute.handle(request, response);
        verifyRequestAndResponse(message);
    }

    private void verifyRequestAndResponse(String body) {
        verify(request).params("id");
        verify(response).status(400);
        verify(response).body(body);
    }

    private void mockRequest(int gameId, String body, Map map) {
        when(useCaseFactory.buildGuessNumberUseCase()).thenReturn(guessNumberUseCase);
        when(request.params("id")).thenReturn(String.valueOf(gameId));
        when(request.body()).thenReturn(body);
        when(serializer.deserialize(body, Map.class)).thenReturn(Optional.ofNullable(map));
    }
}
