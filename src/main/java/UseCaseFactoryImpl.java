import game.GuessValidator;
import game.NumberGateway;
import game.RandomNumberGateway;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;

    public UseCaseFactoryImpl() {
        numberGateway = new RandomNumberGateway();
        guessValidator = new GuessValidator();
    }

    public GenerateNumberUseCase buildGenerateNumberInteractor() {
        return new GenerateNumberInteractor(numberGateway);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        return new GuessNumberInteractor(guessValidator);

    }
}
