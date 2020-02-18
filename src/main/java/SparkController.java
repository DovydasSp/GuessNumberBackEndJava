import game.Constants;
import spark.Service;

public class SparkController {
    private final UseCaseFactory factory;
    private final Service service;
    private final JSONSerializer serializer;

    public SparkController(UseCaseFactory factory, JSONSerializer serializer) {
        this.factory = factory;
        this.serializer = serializer;
        service = Service.ignite();
    }

    public void matchRoutes(int port) {
        service.port(port);

        service.path(Constants.GAMES_PATH, () -> {
            service.post("", new CreateGameRoute(factory, serializer));
            service.post(Constants.GAMES_GUESSES_PATH, new GuessNumberRoute(factory, serializer));
        });

        service.awaitInitialization();
    }

    public void stop() {
        service.stop();
    }

    public int getPort() {
        return service.port();
    }
}
