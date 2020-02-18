import game.GameEntityRepository;
import game.GuessResponseEntity;
import game.GuessValidator;

public class GuessNumberInteractor implements GuessNumberUseCase {

    GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
    }

    @Override
    public GuessResponseEntity checkGuessAndReturnResponse(int gameId, int guessNumber) {
        //TODO complete
        throw new UnsupportedOperationException();
    }
}
