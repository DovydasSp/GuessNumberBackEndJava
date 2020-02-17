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

        service.path(Constants.CREATE_GAME_POST_PATH, () -> {
            service.post("", new CreateGameRoute(factory, serializer));
            service.post(Constants.GUESS_NUMBER_POST_PATH, new GuessNumberRoute(factory, serializer));
        });

        service.awaitInitialization();

    }

    public void stop() {
        service.stop();
    }
}
