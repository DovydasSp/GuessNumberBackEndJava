import spark.Service;

import static spark.Spark.awaitInitialization;
import static spark.Spark.port;

public class SparkController {
    private final UseCaseFactory factory;
    private final Service ignite;

    public SparkController(UseCaseFactory factory) {
        this.factory = factory;
        ignite = Service.ignite();
    }

    public void matchRoutes() {
        port(4568);

        ignite.post("/games", new PostGame(factory));
        ignite.post("/games/:id/guesses", new GuessNumberRoute(factory));

        awaitInitialization();
    }
}
