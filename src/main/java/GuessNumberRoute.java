import spark.Request;
import spark.Response;
import spark.Route;

public class GuessNumberRoute implements Route {
    private final UseCaseFactory useCaseFactory;

    public GuessNumberRoute(UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String id = request.params("id");
        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberInteractor();
        interactor.execute(id);
        return response.body();
    }
}