import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class CreateGameRoute implements Route {
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
        if (serializedGameId.equals("")) {
            response.status(500);
            response.body("Serialization failed.");
        }
        response.body(serializeGameId(gameId));
        return response.body();
    }

    private Map<String, Integer> convertToResponseMap(int gameId) {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", gameId);
        return values;
    }

    private String serializeGameId(int gameId) {
        return serializer.serialize(convertToResponseMap(gameId))
                .orElse("");
    }
}
