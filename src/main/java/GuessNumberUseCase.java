import game.GuessResponseEntity;

public interface GuessNumberUseCase {
    GuessResponseEntity checkGuessAndReturnResponse(String id);
}
