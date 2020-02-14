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
class PostGameRouteTest {
    private PostGameRoute postGameRoute;
    @Mock
    private UseCaseFactory useCaseFactory;
    @Mock
    private GameEntitySerializer serializer;
    @Mock
    private GenerateNumberUseCase generateNumberUseCase;
    @Mock
    private Request request;
    @Mock
    private Response response;

    @BeforeEach
    void setUp() {
        when(useCaseFactory.buildGenerateNumberInteractor()).thenReturn(generateNumberUseCase);
        postGameRoute = new PostGameRoute(useCaseFactory, serializer);
    }

    @Test
    void handle() {
        postGameRoute.handle(request, response);
        verify(generateNumberUseCase).execute();
    }
}
