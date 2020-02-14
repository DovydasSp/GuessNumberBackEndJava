import game.RestGameEntity;
import spark.Request;
import spark.Response;
import spark.Route;

public class GuessNumberRoute implements Route {
    private final UseCaseFactory useCaseFactory;
    private final GameEntitySerializer serializer;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, GameEntitySerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String id = request.params("id");
        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberInteractor();
        interactor.execute(id);
        return response.body();
    }

    private RestGameEntity convertGameEntity(Object gameId) {
        return new RestGameEntity(gameId);
    }
}
