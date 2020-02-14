import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

public class GuessNumberRoute implements Route {
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String id = request.params("id");
        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberInteractor();
        Object result = interactor.execute(id);
        response.body(serializer.serialize(convertToValueMap(result)));
        return response.body();
    }

    private Map<String, Object> convertToValueMap(Object result) {
        Map<String, Object> values = new HashMap<>();
        values.put("result", result);
        return values;
    }
}
