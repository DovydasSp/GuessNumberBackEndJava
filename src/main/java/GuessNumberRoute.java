import com.fasterxml.jackson.databind.ObjectMapper;
import game.GuessResponseEntity;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;

import static java.util.Objects.nonNull;

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
        int id = 0;
        if (nonNull(idStr))
            id = Integer.parseInt(idStr);
        else {
            response.status(400);
            response.body("Invalid Game ID");
            return response;
        }

        int guessNumber = getGuessNumber(request);
        if (guessNumber == 0) {
            response.status(400);
            response.body("Guess invalid");
            return response;
        }

        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberUseCase();
        GuessResponseEntity result = interactor.checkGuessAndReturnResponse(id, guessNumber);
        response.body(serializedResult(result));
        return response.body();
    }

    private int getGuessNumber(Request request) throws com.fasterxml.jackson.core.JsonProcessingException {
        String guessNumberStr = null;
        if (nonNull(request.body())) {
            Map map = new ObjectMapper().readValue(request.body(), Map.class);
            guessNumberStr = (String) map.get("guessNumber");
        }
        if (nonNull(guessNumberStr))
            return Integer.parseInt(guessNumberStr);
        return 0;
    }

    private String serializedResult(GuessResponseEntity result) {
        return serializer.serialize(result)
                .orElse("");
    }
}
