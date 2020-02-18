import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.FakeGameIdProvider;
import game.FakeNumberGateway;
import game.GuessValidator;
import game.InMemoryGameEntityRepo;

class AcceptanceTestSetUp {
    SparkController setUpAndReturnSparkController(int port) {
        UseCaseFactory factory = new UseCaseFactoryImpl(new FakeNumberGateway(), new GuessValidator(),
                new InMemoryGameEntityRepo(), new FakeGameIdProvider());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JSONSerializer serializer = new JacksonJSONSerializer(objectMapper);
        SparkController sparkController = new SparkController(factory, serializer);
        sparkController.matchRoutes(port);
        return sparkController;
    }
}
