import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GuessNumberAcceptanceTest {
    @BeforeEach
    void setUp() {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        JSONSerializer serializer = new JacksonJSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }

    @Test
    void name() {
        /*HttpResponse<String> response = Unirest.post("http://localhost:4568/games")
                .asString();
        System.out.println(response.getBody());
        assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));*/
    }
}
