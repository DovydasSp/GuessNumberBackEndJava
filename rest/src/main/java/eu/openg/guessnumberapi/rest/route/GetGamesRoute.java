package eu.openg.guessnumberapi.rest.route;

import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.rest.exception.ServerErrorException;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spark.Request;
import spark.Response;
import spark.Route;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class GetGamesRoute implements Route {
    private static final Logger LOGGER = LogManager.getLogger(CreateGameRoute.class);
    private final UseCaseFactory useCaseFactory;
    private final JSONSerializer serializer;
    private final RestResponseConverter restResponseConverter;

    public GetGamesRoute(UseCaseFactory useCaseFactory, JSONSerializer serializer,
                         RestResponseConverter restResponseConverter) {
        this.useCaseFactory = useCaseFactory;
        this.serializer = serializer;
        this.restResponseConverter = restResponseConverter;
    }

    @Override
    public Object handle(Request request, Response response) {
        LOGGER.info("Get games request accepted. Fetching games.");
        fillSuccessfulResponse(response, fetchGames());
        return response.body();
    }

    private String fetchGames() {
        return Optional.of(useCaseFactory.buildGetGamesUseCase())
                .map(GetGamesUseCase::fetchGames)
                .map((Function<List<BoundaryGame>, Object>) restResponseConverter::convert)
                .flatMap(serializer::serialize)
                .orElseThrow(() -> new ServerErrorException("Failed to retrieve games."));
    }

    private void fillSuccessfulResponse(Response response, String serializedGames) {
        response.status(200);
        response.body(serializedGames);
    }
}
