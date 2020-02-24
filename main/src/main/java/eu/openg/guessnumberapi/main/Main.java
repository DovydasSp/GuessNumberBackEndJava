package eu.openg.guessnumberapi.main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openg.guessnumberapi.gateway.implementation.PostgresqlGameEntityRepo;
import eu.openg.guessnumberapi.gateway.implementation.RandomNumberGateway;
import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.JacksonJSONSerializer;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import eu.openg.guessnumberapi.usecase.implementation.GuessValidator;
import eu.openg.guessnumberapi.usecase.implementation.UseCaseFactoryImpl;

import static java.util.Objects.nonNull;

public class Main {
    private static SparkController sparkController;

    public static void main(String[] args) {
        UseCaseFactory factory = new UseCaseFactoryImpl(new RandomNumberGateway(), new GuessValidator(),
                new PostgresqlGameEntityRepo());
        //new InMemoryGameEntityRepo(new AtomicGameIdProvider()));
        JSONSerializer serializer = createSerializer();
        sparkController = new SparkController(factory, serializer);
        sparkController.matchRoutes(4568);

        Runtime.getRuntime().addShutdownHook(new Thread(Main::stop));
    }

    private static void stop() {
        if (nonNull(sparkController))
            sparkController.stop();
    }

    private static JSONSerializer createSerializer() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return new JacksonJSONSerializer(objectMapper);
    }
}
