package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.route.RouteConstants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class CreateGameAcceptanceTest extends AcceptanceTestSetUp {
    @Test
    void createNewGameWithId0() {
        HttpResponse<String> response = Unirest.post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH).asString();

        assertThatJson(response.getBody()).node("gameId").isEqualTo(10);
    }
}
