import game.Constants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class CreateGameAcceptanceTest extends AcceptanceTestSetUp {
    CreateGameAcceptanceTest() {
        super(0);
    }

    @Test
    void createNewGameWithId0() {
        HttpResponse<String> response = Unirest.post("http://localhost:" + getPort() + Constants.GAMES_PATH).asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(0);
    }
}
