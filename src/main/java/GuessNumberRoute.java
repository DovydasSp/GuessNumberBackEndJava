import game.GuessResponseEntity;
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
        GuessResponseEntity result = interactor.checkGuessAndReturnResponse(id);
        response.body(serializer.serialize(result));
        return response.body();
    }
}
