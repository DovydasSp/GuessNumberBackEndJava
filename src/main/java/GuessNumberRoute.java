import com.fasterxml.jackson.core.JsonProcessingException;
import game.GuessResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;

public class GuessNumberRoute implements Route {
    private static final Logger LOGGER = LogManager.getLogger(GuessNumberRoute.class);
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {
        String idStr = request.params("id");
        int id;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            LOGGER.error("Tried to parse invalid game ID");
            return changeResponseOnInvalidRequest(response, "Invalid Game ID").body();
        }

        int guessNumber = getGuessNumber(request);
        if (guessNumber == 0) {
            LOGGER.error("Got invalid guess");
            return changeResponseOnInvalidRequest(response, "Guess invalid").body();
        }

        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberUseCase();
        GuessResponseEntity result = interactor.checkGuessAndReturnResponse(id, guessNumber);
        response.body(serializedResult(result));
        return response.body();
    }

    private int getGuessNumber(Request request) throws JsonProcessingException {
        String body = request.body();
        if (nonNull(body)) {
            Map map = serializer.deserializeRequestBody(body);
            String guessNumberStr = (String) map.get("guessNumber");
            if (nonNull(guessNumberStr))
                try {
                    return Integer.parseInt(guessNumberStr);
                } catch (NumberFormatException ex) {
                    LOGGER.error("Tried to parse invalid guess number", ex);
                    return 0;
                }
        }
        return 0;
    }

    private String serializedResult(GuessResponseEntity result) {
        return serializer.serialize(result)
                .orElse("");
    }

    private Response changeResponseOnInvalidRequest(Response response, String message) {
        response.status(400);
        response.body(serializer.serialize(convertToResponseMap(message))
                .orElse(""));
        return response;
    }

    private Map<String, String> convertToResponseMap(String message) {
        Map<String, String> values = new HashMap<>();
        values.put("message", message);
        return values;
    }
}
