import game.GuessResponseEntity;

public interface GuessNumberUseCase {
    GuessResponseEntity checkGuessAndReturnResponse(int gameId, int guessNumber);
}
