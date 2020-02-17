import game.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class GuessNumberAcceptanceTest {
    private static NumberGateway numberGateway;
    private static GuessValidator guessValidator;
    private static GameEntityRepository gameEntityRepository;
    private static IIdProvider idProvider;
    private static UseCaseFactory factory;
    private static JSONSerializer serializer;

    @BeforeAll
    static void setUp() {
        numberGateway = new FakeNumberGateway();
        guessValidator = new GuessValidator();
        gameEntityRepository = new InMemoryGameEntityRepo();
        idProvider = new FakeIdProvider();

        factory = new UseCaseFactoryImpl(numberGateway, guessValidator, gameEntityRepository, idProvider);
        serializer = new JacksonJSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }

    @Test
    void checkGuessedNumber() {
        HttpResponse<String> response = Unirest.post("http://localhost:4568/games").asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));
    }
}
