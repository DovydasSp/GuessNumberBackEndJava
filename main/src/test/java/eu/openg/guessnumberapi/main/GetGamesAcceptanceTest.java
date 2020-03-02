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
        assertThatJson(response.getBody()).isArray().contains("{\"gameId\":11,\"guessCount\":1,\"actualNumber\":11}");
        assertThatJson(response.getBody()).isArray().contains("{\"gameId\":22,\"guessCount\":2,\"actualNumber\":22}");
    }

    private HttpResponse<String> sendGetGamesRequest() {
        String url = "http://localhost:" + getPort() + RouteConstants.GAMES_PATH;
        return Unirest.get(url).asString();
    }
}
