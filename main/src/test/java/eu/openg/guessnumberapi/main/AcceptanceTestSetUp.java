package eu.openg.guessnumberapi.main;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.openg.guessnumberapi.gateway.fake.FakeGameIdProvider;
import eu.openg.guessnumberapi.gateway.fake.FakeNumberGateway;
import eu.openg.guessnumberapi.gateway.implementation.InMemoryGameRepo;
import eu.openg.guessnumberapi.rest.entity.JSONSerializer;
import eu.openg.guessnumberapi.rest.entity.JacksonJSONSerializer;
import eu.openg.guessnumberapi.rest.entity.converter.GuessResponseConverter;
import eu.openg.guessnumberapi.rest.entity.converter.RestResponseConverter;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;
import eu.openg.guessnumberapi.usecase.implementation.GuessValidator;
import eu.openg.guessnumberapi.usecase.implementation.UseCaseFactoryImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class AcceptanceTestSetUp {
    private static SparkController sparkController;

    @BeforeAll
    public static void setUp() {
        sparkController = setUpAndReturnSparkController();
    }

    @AfterAll
    public static void tearDown() {
        sparkController.stop();
    }

    private static SparkController setUpAndReturnSparkController() {
        UseCaseFactory factory = new UseCaseFactoryImpl(new FakeNumberGateway(), new GuessValidator(),
                new InMemoryGameRepo(new FakeGameIdProvider()));

        final ObjectMapper objectMapper = buildObjectMapper();

        JSONSerializer serializer = new JacksonJSONSerializer(objectMapper);
        RestResponseConverter restResponseConverter = new GuessResponseConverter();
        SparkController sparkController = new SparkController(factory, serializer, restResponseConverter);
        sparkController.matchRoutes(0);
        return sparkController;
    }

    private static ObjectMapper buildObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return objectMapper;
    }

    int getPort() {
        return sparkController.getPort();
    }
}
