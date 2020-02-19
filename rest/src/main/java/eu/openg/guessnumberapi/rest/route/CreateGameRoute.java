package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGameEntity;
import eu.openg.guessnumberapi.rest.exception.RestErrorResponseEntity;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

import static java.util.Objects.isNull;

public class CreateGameRoute implements Route {
    private static final Logger LOGGER = LogManager.getLogger(CreateGameRoute.class);
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public CreateGameRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) {
        CreateGameUseCase interactor = useCaseFactory.buildCreateGameUseCase();
        int gameId = interactor.createGameAndReturnGameId();
        String serializedGameId = serializeGameId(gameId);
        if (isNull(serializedGameId)) {
            LOGGER.error("Failed to create game ID");
            changeResponseOnInvalidRequest(response);
        } else {
            response.body(serializedGameId);
        }
        return response.body();
    }

    private String serializeGameId(int gameId) {
        return serializer.serialize(new RestGameEntity(gameId, null, null))
                .orElse(null);
    }

    private Response changeResponseOnInvalidRequest(Response response) {
        response.status(500);
        response.body(serializer.serialize(new RestErrorResponseEntity("Serialization failed."))
                .orElse(""));
        return response;
    }
}
