import spark.Request;
import spark.Response;
import spark.Route;

public class PostGame implements Route {
    private final UseCaseFactory useCaseFactory;

    public PostGame(UseCaseFactory useCaseFactory) {
        this.useCaseFactory = useCaseFactory;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        response.body();
        //GenerateNumberUseCase interactor = factory.buildInteractor(); //Kurti su kiekvienu routu
        return null;
    }
}
