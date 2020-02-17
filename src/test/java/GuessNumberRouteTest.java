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
        when(useCaseFactory.buildGuessNumberInteractor()).thenReturn(guessNumberUseCase);
        guessNumberRoute = new GuessNumberRoute(useCaseFactory, serializer);
    }

    @Test
    void routeReceivesGameIdAndCallsCheckerWithIt() throws Exception {
        when(request.params("id")).thenReturn("37");
        when(request.body()).thenReturn("{\"guessNumber\":\"5\"}");
        guessNumberRoute.handle(request, response);
        verify(request).params("id");
        verify(guessNumberUseCase).checkGuessAndReturnResponse(37, 5);
    }
}
