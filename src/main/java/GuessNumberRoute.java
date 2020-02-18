import game.ErrorResponseEntity;
import game.GuessResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Map;
import java.util.Optional;

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
    public Object handle(Request request, Response response) {
        String idStr = request.params("id");
        int id = parseIntFromStringOr0IfException(idStr, "game ID");
        if (id == 0) {
            LOGGER.error("Got invalid id");
            return changeResponseOnInvalidRequest(response, 400, new ErrorResponseEntity("invalid ID")).body();
        }

        int guessNumber = getGuessNumber(request);
        if (guessNumber == 0) {
            LOGGER.error("Got invalid guess");
            return changeResponseOnInvalidRequest(response, 400, new ErrorResponseEntity("invalid guess")).body();
        }

        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberUseCase();
        GuessResponseEntity result = interactor.checkGuessAndReturnResponse(id, guessNumber);
        return changeResponseOnInvalidRequest(response, 200, result).body();
    }

    private int getGuessNumber(Request request) {
        String body = request.body();
        if (nonNull(body)) {
            Optional<Map> map = serializer.deserialize(body, Map.class);
            Map guessNumberMap;
            String guessNumberStr = null;
            if (map.isPresent()) {
                guessNumberMap = map.get();
                guessNumberStr = (String) guessNumberMap.get("guessNumber");
            }

            return parseIntFromStringOr0IfException(guessNumberStr, "guess number");
        }
        return 0;
    }

    private Response changeResponseOnInvalidRequest(Response response, int statusCode, Object obj) {
        response.status(statusCode);
        response.body(serializer.serialize(obj)
                .orElse(""));
        return response;
    }

    private int parseIntFromStringOr0IfException(String parsableString, String logMsg) {
        try {
            return Integer.parseInt(parsableString);
        } catch (NumberFormatException ex) {
            LOGGER.error("Tried to parse invalid " + logMsg, ex);
            return 0;
        }
    }
}
