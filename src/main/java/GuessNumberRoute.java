import com.fasterxml.jackson.databind.ObjectMapper;
import game.GuessResponseEntity;
import spark.Request;
import spark.Response;
import spark.Route;

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
        String idStr = request.params("id");
        int id = Integer.parseInt(idStr);

        int guessNumber = getGuessNumber(request);

        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberInteractor();
        GuessResponseEntity result = interactor.checkGuessAndReturnResponse(id, guessNumber);
        response.body(serializer.serialize(result));
        return response.body();
    }

    private int getGuessNumber(Request request) throws com.fasterxml.jackson.core.JsonProcessingException {
        Map map = new ObjectMapper().readValue(request.body(), Map.class);
        String guessNumberStr = (String) map.get("guessNumber");
        return Integer.parseInt(guessNumberStr);
    }
}
