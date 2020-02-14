import game.RestGameEntity;
import spark.Request;
import spark.Response;
import spark.Route;

public class GuessNumberRoute implements Route {
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String id = request.params("id");
        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberInteractor();
        Object responseObj = interactor.execute(id);
        response.body(serializer.serialize(convertGameEntity(responseObj)));
        return response.body();
    }

    private RestGameEntity convertGameEntity(Object gameId) {
        return new RestGameEntity(gameId);
    }
}
