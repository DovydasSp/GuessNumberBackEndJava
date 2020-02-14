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

    public CreateGameUseCase buildGenerateNumberInteractor() {
        return new CreateGameInteractor(numberGateway, gameEntityRepository);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }


}
