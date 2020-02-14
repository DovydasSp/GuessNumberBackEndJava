import game.RestGameEntity;
import spark.Request;
import spark.Response;
import spark.Route;

public class PostGameRoute implements Route {
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public PostGameRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) {
        CreateGameUseCase interactor = useCaseFactory.buildGenerateNumberInteractor();
        int gameId = interactor.execute();
        response.body(serializer.serialize(convertGameEntity(gameId)));
        return response.body();
    }

    private RestGameEntity convertGameEntity(Object gameId) {
        return new RestGameEntity(gameId);
    }
}
