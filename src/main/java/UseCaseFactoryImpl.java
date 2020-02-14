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

    public UseCaseFactoryImpl(NumberGateway numberGateway, GuessValidator guessValidator, GameEntityRepository gameEntityRepository) {
        this.numberGateway = numberGateway;
        this.guessValidator = guessValidator;
        this.gameEntityRepository = gameEntityRepository;
    }

    public CreateGameUseCase buildCreateGameInteractor() {
        return new CreateGameInteractor(numberGateway, gameEntityRepository);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }


}
