import game.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateGameAcceptanceTest {
    private NumberGateway numberGateway;
    private GuessValidator guessValidator;
    private GameEntityRepository gameEntityRepository;
    private IIdProvider idProvider;
    private UseCaseFactory factory;
    private JSONSerializer serializer;

    @BeforeEach
    void setUp() {
        numberGateway = new FakeNumberGateway();
        guessValidator = new GuessValidator();
        gameEntityRepository = new InMemoryGameEntityRepo();
        idProvider = new FakeIdProvider();

        factory = new UseCaseFactoryImpl(numberGateway, guessValidator, gameEntityRepository, idProvider);
        serializer = new JacksonJSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }

    @Test
    void name() {
        HttpResponse<String> response = Unirest.post("http://localhost:4568/games").asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));
        assertEquals(gameEntityRepository.getEntity(0).returnGeneratedNumber(), 3);
        assertEquals(gameEntityRepository.getEntity(0).returnGuessCount(), 0);
    }
}
