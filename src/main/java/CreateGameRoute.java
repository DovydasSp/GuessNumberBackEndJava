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
        CreateGameUseCase interactor = useCaseFactory.buildCreateGameInteractor();
        int gameId = interactor.createGameAndReturnGameId();
        response.body(serializer.serialize(convertToResponseMap(gameId)));
        return response.body();
    }

    private Map<String, Integer> convertToResponseMap(int gameId) {
        Map<String, Integer> values = new HashMap<>();
        values.put("gameId", gameId);
        return values;
    }
}
