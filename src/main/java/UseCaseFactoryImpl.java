import game.*;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;
    private final GameEntityRepository gameEntityRepository;
    private final GameIdProvider gameIdProvider;

    public UseCaseFactoryImpl() {
        this(new RandomNumberGateway(), new GuessValidator(), new InMemoryGameEntityRepo(), new AtomicGameIdProvider());
    }

    public UseCaseFactoryImpl(NumberGateway numberGateway, GuessValidator guessValidator,
                              GameEntityRepository gameEntityRepository, GameIdProvider gameIdProvider) {
        this.numberGateway = numberGateway;
        this.guessValidator = guessValidator;
        this.gameEntityRepository = gameEntityRepository;
        this.gameIdProvider = gameIdProvider;
    }

    public CreateGameUseCase buildCreateGameUseCase() {
        return new CreateGameInteractor(numberGateway, gameEntityRepository, gameIdProvider);
    }

    public GuessNumberUseCase buildGuessNumberUseCase() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }


}
