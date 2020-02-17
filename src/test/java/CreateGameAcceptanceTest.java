import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class CreateGameAcceptanceTest {
    @BeforeEach
    void setUp() {
        UseCaseFactory factory = new UseCaseFactoryImpl();
        JSONSerializer serializer = new JacksonJSONSerializer();
        new SparkController(factory, serializer).matchRoutes();
    }

    @Test
    void name() {
        HttpResponse<String> response = Unirest.post("http://localhost:4568/games")
                .asString();
        assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));
    }
}
