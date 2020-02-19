package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.route.RouteConstants;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class GuessNumberAcceptanceTest extends AcceptanceTestSetUp {
    @Test
    void createNewGameWithId10AndSendHigherGuess() {
        Unirest.post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH).asString();

        HttpResponse<String> response = Unirest
                .post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH + "/" + 10 + "/guesses")
                .body("{\"guessNumber\":\"5\"}").asString();

        assertThatJson(response.getBody()).node("message").isEqualTo("Lower");
    }

    @Test
    void createNewGameWithId10AndSendLowerGuess() {
        Unirest.post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH).asString();

        HttpResponse<String> response = Unirest
                .post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH + "/" + 10 + "/guesses")
                .body("{\"guessNumber\":\"1\"}").asString();

        assertThatJson(response.getBody()).node("message").isEqualTo("Higher");
    }

    @Test
    void createNewGameWithId10AndSendCorrectGuess() {
        Unirest.post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH).asString();

        HttpResponse<String> response = Unirest
                .post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH + "/" + 10 + "/guesses")
                .body("{\"guessNumber\":\"3\"}").asString();

        assertThatJson(response.getBody()).node("numberOfGuesses").isNotNull();
    }
}
