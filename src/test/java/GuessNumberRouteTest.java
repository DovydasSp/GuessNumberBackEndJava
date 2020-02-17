import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GuessNumberRouteTest {

    private GuessNumberRoute guessNumberRoute;
    @Mock
    private UseCaseFactory useCaseFactory;
    @Mock
    private JSONSerializer serializer;
    @Mock
    private GuessNumberUseCase guessNumberUseCase;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @BeforeEach
    void setUp() {
        guessNumberRoute = new GuessNumberRoute(useCaseFactory, serializer);
    }

    @Test
    void routeReceivesGameIdAndCallsCheckerWithIt() throws Exception {
        int gameId = 37;
        int guessNumber = 5;
        when(useCaseFactory.buildGuessNumberUseCase()).thenReturn(guessNumberUseCase);
        when(request.params("id")).thenReturn(String.valueOf(gameId));
        when(request.body()).thenReturn("{\"guessNumber\":\"" + guessNumber + "\"}");
        when(serializer.fetchObjectMapper()).thenReturn(new ObjectMapper());
        guessNumberRoute.handle(request, response);
        verify(request).params("id");
        verify(guessNumberUseCase).checkGuessAndReturnResponse(gameId, guessNumber);
    }

    @Test
    void routeReceivesGameIdNullAndCallsCheckerWithIt() throws Exception {
        guessNumberRoute.handle(request, response);
        verifyRequestAndResponse("Invalid Game ID");
    }

    @Test
    void routeReceivesNullGuessAndCallsCheckerWithIt() throws Exception {
        when(request.params("id")).thenReturn("1");
        guessNumberRoute.handle(request, response);
        verifyRequestAndResponse("Guess invalid");
    }

    private void verifyRequestAndResponse(String body) {
        verify(request).params("id");
        verify(response).status(400);
        verify(response).body(body);
    }
}
