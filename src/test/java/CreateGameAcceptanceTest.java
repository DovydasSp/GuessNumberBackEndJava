import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import game.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class CreateGameAcceptanceTest {
    private NumberGateway numberGateway;
    private GuessValidator guessValidator;
    private GameEntityRepository gameEntityRepository;
    private GameIdProvider gameIdProvider;
    private UseCaseFactory factory;
    private JSONSerializer serializer;
    private SparkController sparkController;

    @AfterEach
    void tearDown() {
        sparkController.stop();
    }


    @BeforeEach
    void setUp() {
        numberGateway = new FakeNumberGateway();
        guessValidator = new GuessValidator();
        gameEntityRepository = new InMemoryGameEntityRepo();
        gameIdProvider = new FakeGameIdProvider();

        factory = new UseCaseFactoryImpl(numberGateway, guessValidator, gameEntityRepository, gameIdProvider);

        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        serializer = new JacksonJSONSerializer(objectMapper);
        sparkController = new SparkController(factory, serializer);
        sparkController.matchRoutes(4567);
    }

    @Test
    void createNewGameWithId0() {
        HttpResponse<String> response = Unirest.post("http://localhost:4567/games").asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));
    }
}
