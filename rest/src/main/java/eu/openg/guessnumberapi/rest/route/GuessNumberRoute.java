package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGuessRequest;
import eu.openg.guessnumberapi.rest.entity.RestGuessResponse;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.rest.exception.GameNotFountException;
import eu.openg.guessnumberapi.rest.exception.InvalidParamException;
import eu.openg.guessnumberapi.rest.exception.MissingParamException;
import eu.openg.guessnumberapi.rest.exception.ServerErrorException;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

public class GuessNumberRoute implements Route {
    private static final Logger LOGGER = LogManager.getLogger(GuessNumberRoute.class);
    private static final String PARAM_ID = "id";
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;
    private final RestResponseConverter restGuessResponse;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer,
                            RestResponseConverter restGuessResponse) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
        this.restGuessResponse = restGuessResponse;
    }

    @Override
    public Object handle(Request request, Response response) {
        int id = extractIdParam(request);
        int guessNumber = extractAndValidateGuessNumber(request);
        LOGGER.info("Request accepted. ID: " + id + " guessNumber: " + guessNumber);
        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberUseCase();
        BoundaryGuessResponse boundaryGuessResponse = Optional.of(interactor)
                .map(inter -> inter.checkGuessAndReturnResponse(id, guessNumber))
                .orElseThrow(() -> new GameNotFountException(id));
        RestGuessResponse result = restGuessResponse.convert(boundaryGuessResponse);
        return serializeAndSetResponse(response, result).body();
    }

    private int extractIdParam(Request request) {
        return Optional.ofNullable(request.params(PARAM_ID))
                .map(this::parseToInt)
                .orElseThrow(() -> new MissingParamException(PARAM_ID));
    }

    private int parseToInt(String paramValue) {
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException ex) {
            throw new InvalidParamException(GuessNumberRoute.PARAM_ID);
        }
    }

    private int extractAndValidateGuessNumber(Request request) {
        return serializer.deserialize(request.body(), RestGuessRequest.class)
                .map(RestGuessRequest::getGuessNumber)
                .filter(guessNumber -> guessNumber > 0 && guessNumber <= 10)
                .orElseThrow(() -> new InvalidParamException("guessNumber"));
    }

    private Response serializeAndSetResponse(Response response, Object obj) {
        response.status(200);
        response.body(serializer.serialize(obj)
                .orElseThrow(ServerErrorException::new));
        return response;
    }
}
