import game.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class GuessNumberAcceptanceTest {
    private static SparkController sparkController;

    @BeforeAll
    static void setUp() {
        sparkController = new AcceptanceTestSetUp().setUpAndReturnSparkController(4569);
    }

    @AfterEach
    void tearDown() {
        sparkController.stop();
    }

    @Test
    void checkGuessedNumber() {
        HttpResponse<String> response = Unirest.post("http://localhost:4569" + Constants.GUESS_NUMBER_POST_PATH).asString();
        //TODO complete
        //assertThatJson(response.getBody()).node("gameId").isEqualTo(BigDecimal.valueOf(0));
    }
}
