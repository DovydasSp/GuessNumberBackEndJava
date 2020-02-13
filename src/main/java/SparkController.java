import static spark.Spark.post;

public class SparkController {
    private InteractorInterface interactor;

    public SparkController() {
        FactoryInterface factory = new Factory();
        interactor = factory.buildInteractor();
    }

    public void requests() {
        post("/play", (request, response) -> {
            return 0;
        });

        post("/games/:id/guess", (request, response) -> {
            return request.params(":id");
        });
    }


}
