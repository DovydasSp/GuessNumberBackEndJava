package eu.openg.guessnumberapi.main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.implementation.AtomicGameIdProvider;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.gateway.implementation.PostgreSQL.PostgresqlConnectionProvider;
import eu.openg.guessnumberapi.gateway.implementation.PostgreSQL.PostgresqlGameRepo;
import eu.openg.guessnumberapi.gateway.implementation.RandomNumberGateway;
import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.JacksonJSONSerializer;
import eu.openg.guessnumberapi.rest.entity.converter.GamesResponseConverter;
import eu.openg.guessnumberapi.rest.entity.converter.GuessResponseConverter;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import eu.openg.guessnumberapi.usecase.implementation.GuessValidator;
import eu.openg.guessnumberapi.usecase.implementation.UseCaseFactoryImpl;

import static java.util.Objects.nonNull;

public class Main {
    private static SparkController sparkController;
    private static GameRepository gameRepository;
    private static PostgresqlConnectionProvider postgresqlConnectionProvider;

    public static void main(String[] args) {
        postgresqlConnectionProvider = new PostgresqlConnectionProvider(Config.POSTGRES_URL, Config.POSTGRES_USERNAME,
                Config.POSTGRES_PASSWORD);
        gameRepository = initGameRepository();
        UseCaseFactory factory = new UseCaseFactoryImpl(new RandomNumberGateway(), new GuessValidator(), gameRepository);
        JSONSerializer serializer = createSerializer();
        RestResponseConverter restResponseConverter = new GuessResponseConverter();
        RestResponseConverter restGamesConverter = new GamesResponseConverter();
        sparkController = new SparkController(factory, serializer, restResponseConverter, restGamesConverter);
        sparkController.matchRoutes(4568);

        Runtime.getRuntime().addShutdownHook(new Thread(Main::stop));
    }

    private static void stop() {
        if (nonNull(sparkController))
            sparkController.stop();
        if (nonNull(gameRepository))
            postgresqlConnectionProvider.closeConnection();
    }

    private static JSONSerializer createSerializer() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new JacksonJSONSerializer(objectMapper);
    }

    private static GameRepository initGameRepository() {
        if (Config.USE_POSTGRESQL_DB)
            return new PostgresqlGameRepo(postgresqlConnectionProvider);
        else
            return new InMemoryGameRepo(new AtomicGameIdProvider());
    }
}
