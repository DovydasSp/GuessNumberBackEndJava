import game.*;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;
    private final GameEntityRepository gameEntityRepository;

    public UseCaseFactoryImpl() {
        numberGateway = new RandomNumberGateway();
        guessValidator = new GuessValidator();
        gameEntityRepository = new InMemoryGameEntityRepo();
    }

    public GenerateNumberUseCase buildGenerateNumberInteractor() {
        return new GenerateNumberInteractor(numberGateway, gameEntityRepository);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }


}
