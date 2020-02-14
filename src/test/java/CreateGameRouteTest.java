import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

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
        when(useCaseFactory.buildGenerateNumberInteractor()).thenReturn(generateNumberUseCase);
        postGameRoute = new CreateGameRoute(useCaseFactory, serializer);
    }

    @Test
    void handle1() {
        when(generateNumberUseCase.execute()).thenReturn(8);
        postGameRoute.handle(request, response);
        verify(generateNumberUseCase).execute();
        verify(serializer).serialize(createResponseMap(8));
    }

    @Test
    void handle2() {
        when(generateNumberUseCase.execute()).thenReturn(78578);
        postGameRoute.handle(request, response);
        verify(generateNumberUseCase).execute();
        verify(serializer).serialize(createResponseMap(78578));
    }

    private Map<String, Integer> createResponseMap(int gameId) {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", gameId);
        return values;
    }
}
