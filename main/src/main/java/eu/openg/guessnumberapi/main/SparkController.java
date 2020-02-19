package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.route.CreateGameRoute;
import eu.openg.guessnumberapi.rest.route.GuessNumberRoute;
import eu.openg.guessnumberapi.rest.route.RouteConstants;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
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
        service.exception(RuntimeException.class, new SparkExceptionHandler(serializer));

        service.path(RouteConstants.GAMES_PATH, () -> {
            service.post("", new CreateGameRoute(factory, serializer));
            service.post(RouteConstants.GAMES_GUESSES_PATH, new GuessNumberRoute(factory, serializer));
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
