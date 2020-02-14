import spark.Service;

public class SparkController {
    private final UseCaseFactory factory;
    private final Service ignite;
    private final JSONSerializer serializer;

    public SparkController(UseCaseFactory factory, JSONSerializer serializer) {
        this.factory = factory;
        this.serializer = serializer;
        ignite = Service.ignite();
    }

    public void matchRoutes() {
        ignite.port(4568);

        ignite.post("/games", new CreateGameRoute(factory, serializer));
        ignite.post("/games/:id/guesses", new GuessNumberRoute(factory, serializer));

        System.out.println(ignite.port());
        ignite.awaitInitialization();

    }
}
