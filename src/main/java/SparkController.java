import static spark.Spark.post;

public class SparkController {
    private final UseCaseFactory factory;

    public SparkController(UseCaseFactory factory) {
        this.factory = factory;
    }

    public void matchRoutes() {
        post("/games", (request, response) -> {
            return 0;
            //GameUseCase interactor = factory.buildInteractor(); //Kurti su kiekvienu routu
        });

        post("/games/:id/guesses", (request, response) -> {
            return request.params(":id");
        });
    }


}
