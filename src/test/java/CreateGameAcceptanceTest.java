import game.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class CreateGameAcceptanceTest {
    private static SparkController sparkController;

    @BeforeAll
    static void setUp() {
        sparkController = new AcceptanceTestSetUp().setUpAndReturnSparkController(4567);
    }

    @AfterAll
    static void tearDown() {
        sparkController.stop();
    }

    @Test
    void createNewGameWithId0() {
        HttpResponse<String> response = Unirest.post("http://localhost:4567" + Constants.CREATE_GAME_POST_PATH).asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(0);
    }
}
