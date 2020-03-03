package eu.openg.guessnumberapi.main;

import eu.openg.guessnumberapi.gateway.fake.FakeInMemoryGameRepo;
import eu.openg.guessnumberapi.rest.route.RouteConstants;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class GuessNumberAcceptanceTest extends AcceptanceTestSetUp {
    @Test
    void createNewGameWithId10AndSendHigherGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(3);
        assertThatJson(response.getBody()).node("message").isEqualTo(BoundaryGuessResultStatus.LESS.toString());
    }

    @Test
    void createNewGameWithId10AndSendLowerGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(1);
        assertThatJson(response.getBody()).node("message").isEqualTo(BoundaryGuessResultStatus.MORE.toString());
    }

    @Test
    void createNewGameWithId10AndSendCorrectGuess() {
        HttpResponse<String> response = createNewGameAndSendGuess(2);
        assertThatJson(response.getBody()).node("numberOfGuesses").isEqualTo("2");
    }

    private HttpResponse<String> createNewGameAndSendGuess(int guessNumber) {
        String url = "http://localhost:" + getPort() + RouteConstants.GAMES_PATH;
        Unirest.post(url);

        return Unirest
                .post(url + "/" + FakeInMemoryGameRepo.GAME_ID + "/guesses")
                .body("{\"guessNumber\":\"" + guessNumber + "\"}").asString();
    }
}
