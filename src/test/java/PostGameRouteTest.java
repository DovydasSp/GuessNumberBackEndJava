import game.GameEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spark.Request;
import spark.Response;

import static org.mockito.ArgumentMatchers.anyObject;
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

    private GameEntity gameEntity;

    @BeforeEach
    void setUp() {
        gameEntity = new GameEntity(1, 2, 3);
        when(useCaseFactory.buildGenerateNumberInteractor()).thenReturn(generateNumberUseCase);
        when(generateNumberUseCase.execute()).thenReturn(gameEntity);
        postGameRoute = new PostGameRoute(useCaseFactory, serializer);
    }

    @Test
    void handle() {
        postGameRoute.handle(request, response);
        verify(generateNumberUseCase).execute();
        verify(serializer).serialize(anyObject());
    }
}
