import game.GuessResponseEntity;

public interface GuessNumberUseCase {
    GuessResponseEntity checkGuessAndReturnResponse(int id, int guessNumber);
}
