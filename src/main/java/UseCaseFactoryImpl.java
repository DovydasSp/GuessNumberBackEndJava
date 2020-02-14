import game.*;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;
    private final GameEntityRepository gameEntityRepository;
    private final IIdProvider idProvider;

    public UseCaseFactoryImpl() {
        numberGateway = new RandomNumberGateway();
        guessValidator = new GuessValidator();
        gameEntityRepository = new InMemoryGameEntityRepo();
        idProvider = new IdProvider();
    }

    public UseCaseFactoryImpl(NumberGateway numberGateway, GuessValidator guessValidator, GameEntityRepository gameEntityRepository, IIdProvider idProvider) {
        this.numberGateway = numberGateway;
        this.guessValidator = guessValidator;
        this.gameEntityRepository = gameEntityRepository;
        this.idProvider = idProvider;
    }

    public CreateGameUseCase buildCreateGameInteractor() {
        return new CreateGameInteractor(numberGateway, gameEntityRepository, idProvider);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }


}
