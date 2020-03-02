package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.route.RouteConstants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class GetGamesAcceptanceTest extends AcceptanceTestSetUp {

    @Test
    void getGamesFromDb() {
        HttpResponse<String> response = sendGetGamesRequest();
        assertThatJson(response.getBody()).node("[0]").node("gameId").isEqualTo(10);
        assertThatJson(response.getBody()).node("[0]").node("guessCount").isEqualTo(0);
        assertThatJson(response.getBody()).node("[0]").node("guessCount").isNumber();
    }

    private HttpResponse<String> sendGetGamesRequest() {
        String url = "http://localhost:" + getPort() + RouteConstants.GAMES_PATH;
        Unirest.post(url).asString();
        return Unirest.get(url).asString();
    }
}
