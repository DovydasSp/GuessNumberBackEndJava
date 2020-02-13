import spark.Request;
import spark.Response;
import spark.Route;

public class PostGameRoute implements Route {
    private final UseCaseFactory useCaseFactory;

    public PostGameRoute(UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        GenerateNumberUseCase interactor = useCaseFactory.buildGenerateNumberInteractor();
        interactor.execute();
        return response.body();
    }
}
