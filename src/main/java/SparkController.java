import spark.Service;

public class SparkController {
    private final UseCaseFactory factory;
    private final Service ignite;

    public SparkController(UseCaseFactory factory) {
        this.factory = factory;
        ignite = Service.ignite();
    }

    public void matchRoutes() {
        ignite.port(4568);

        ignite.post("/games", new PostGameRoute(factory));
        ignite.post("/games/:id/guesses", new GuessNumberRoute(factory));

        System.out.println(ignite.port());
        ignite.awaitInitialization();

    }
}
