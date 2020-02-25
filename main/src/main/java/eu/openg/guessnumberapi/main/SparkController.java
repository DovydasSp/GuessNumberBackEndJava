package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.rest.route.AcceptingOptionsRoute;
import eu.openg.guessnumberapi.rest.route.CreateGameRoute;
import eu.openg.guessnumberapi.rest.route.GuessNumberRoute;
import eu.openg.guessnumberapi.rest.route.RouteConstants;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import spark.Service;

import static eu.openg.guessnumberapi.rest.entity.CorsHeadersProvider.CORS_HEADERS;

public class SparkController {
    private final UseCaseFactory factory;
    private final Service service;
    private final JSONSerializer serializer;
    private final RestResponseConverter restResponseConverter;

    public SparkController(UseCaseFactory factory, JSONSerializer serializer, RestResponseConverter restResponseConverter) {
        this.factory = factory;
        this.serializer = serializer;
        this.restResponseConverter = restResponseConverter;
        service = Service.ignite();
    }

    public void matchRoutes(int port) {
        service.port(port);
        service.before((request, response) -> CORS_HEADERS.forEach(response::header));
        service.exception(RuntimeException.class, new SparkExceptionHandler(serializer));

        service.options("*", new AcceptingOptionsRoute());

        service.path(RouteConstants.GAMES_PATH, () -> {
            service.post("", new CreateGameRoute(factory, serializer));
            service.post(RouteConstants.GAMES_GUESSES_PATH, new GuessNumberRoute(factory, serializer, restResponseConverter));
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
