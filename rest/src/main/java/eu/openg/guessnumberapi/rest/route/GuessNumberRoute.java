package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.RestGuessRequestEntity;
import eu.openg.guessnumberapi.rest.entity.RestGuessResponseEntity;
import eu.openg.guessnumberapi.rest.entity.converter.GuessResponseConverter;
import eu.openg.guessnumberapi.rest.exception.InvalidParamException;
import eu.openg.guessnumberapi.rest.exception.MissingParamException;
import eu.openg.guessnumberapi.rest.exception.ServerErrorException;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.Optional;

public class GuessNumberRoute implements Route {
    private static final String PARAM_ID = "id";
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;

    public GuessNumberRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
    }

    @Override
    public Object handle(Request request, Response response) {
        int id = extractIdParam(request);
        int guessNumber = extractAndValidateGuessNumber(request);

        GuessNumberUseCase interactor = useCaseFactory.buildGuessNumberUseCase();
        RestGuessResponseEntity result = new GuessResponseConverter().convert(interactor.checkGuessAndReturnResponse(id, guessNumber));
        return serializeAndSetResponse(response, result).body();
    }

    private int extractAndValidateGuessNumber(Request request) {
        return serializer.deserialize(request.body(), RestGuessRequestEntity.class)
                .map(RestGuessRequestEntity::getGuessNumber)
                .filter(guessNumber -> guessNumber > 0)
                .orElseThrow(() -> new InvalidParamException("guessNumber"));
    }

    private Response serializeAndSetResponse(Response response, Object obj) {
        response.status(200);
        response.body(serializer.serialize(obj)
                .orElseThrow(ServerErrorException::new));
        return response;
    }

    private int parseToInt(String paramValue) {
        try {
            return Integer.parseInt(paramValue);
        } catch (NumberFormatException ex) {
            throw new InvalidParamException(GuessNumberRoute.PARAM_ID);
        }
    }

    private int extractIdParam(Request request) {
        return Optional.ofNullable(request.params(PARAM_ID))
                .map(this::parseToInt)
                .orElseThrow(() -> new MissingParamException(PARAM_ID));
    }
}
