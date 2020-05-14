package eu.openg.guessnumberapi.usecase.api;

public interface GuessNumberUseCase {
    BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber);
}
