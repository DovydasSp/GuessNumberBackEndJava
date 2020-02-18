import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.FakeGameIdProvider;
import game.FakeNumberGateway;
import game.GuessValidator;
import game.InMemoryGameEntityRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

class AcceptanceTestSetUp {
    private static final Logger LOGGER = LogManager.getLogger(AcceptanceTestSetUp.class);
    private static SparkController sparkController;
    private static int port;

    AcceptanceTestSetUp(int port) {
        AcceptanceTestSetUp.port = port;
        LOGGER.info("Port: " + port);
    }

    @BeforeAll
    public static void setUp() {
        sparkController = setUpAndReturnSparkController(port);

    }

    @AfterAll
    public static void tearDown() {
        sparkController.stop();
    }

    private static SparkController setUpAndReturnSparkController(int port) {
        UseCaseFactory factory = new UseCaseFactoryImpl(new FakeNumberGateway(), new GuessValidator(),
                new InMemoryGameEntityRepo(), new FakeGameIdProvider());

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        JSONSerializer serializer = new JacksonJSONSerializer(objectMapper);
        SparkController sparkController = new SparkController(factory, serializer);
        sparkController.matchRoutes(port);
        return sparkController;
    }

    int getPort() {
        return sparkController.getPort();
    }
}
