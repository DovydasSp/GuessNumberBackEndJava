package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.rest.route.RouteConstants;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class GuessNumberAcceptanceTest extends AcceptanceTestSetUp {
    @Test
    void createNewGameWithId10AndSendHigherGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(5);
        assertThatJson(response.getBody()).node("message").isEqualTo(BoundaryGuessResultStatus.HIGHER);
    }

    @Test
    void createNewGameWithId10AndSendLowerGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(1);
        assertThatJson(response.getBody()).node("message").isEqualTo(BoundaryGuessResultStatus.LOWER);
    }

    @Test
    void createNewGameWithId10AndSendCorrectGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(3);
        assertThatJson(response.getBody()).node("message").isEqualTo(BoundaryGuessResultStatus.CORRECT);
        assertThatJson(response.getBody()).node("numberOfGuesses").isEqualTo("2");
    }

    private HttpResponse<String> createNewGameAndSendGuess(int guessNumber) {
        Unirest.post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH).asString();

        HttpResponse<String> response = Unirest
                .post("http://localhost:" + getPort() + RouteConstants.GAMES_PATH + "/" + 10 + "/guesses")
                .body("{\"guessNumber\":\"" + guessNumber + "\"}").asString();

        assertThatJson(response.getBody()).node("numberOfGuesses").isEqualTo(2);

        return response;
    }
}
