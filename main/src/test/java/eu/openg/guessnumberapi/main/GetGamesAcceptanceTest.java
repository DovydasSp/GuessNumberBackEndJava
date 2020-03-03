package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.route.RouteConstants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import net.javacrumbs.jsonunit.assertj.JsonAssert;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class GetGamesAcceptanceTest extends AcceptanceTestSetUp {

    @Test
    void getGamesFromDb() {
        String url = "http://localhost:" + getPort() + RouteConstants.GAMES_PATH;
        HttpResponse<String> response = sendGetGamesRequest(url);
        checkResponse(response, 0, 11, 1, 11);
        checkResponse(response, 1, 22, 2, 22);
    }

    private HttpResponse<String> sendGetGamesRequest(String url) {
        return Unirest.get(url).asString();
    }

    private void checkResponse(HttpResponse<String> response, int node, int gameId, int guessCount, int actualNumber) {
        JsonAssert assertNode = assertThatJson(response.getBody()).node("[" + node + "]");
        assertNode.node("gameId").isEqualTo(gameId);
        assertNode.node("guessCount").isEqualTo(guessCount);
        assertNode.node("actualNumber").isEqualTo(actualNumber);
    }
}
