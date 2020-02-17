import game.GameEntity;
import game.GameEntityRepository;
import game.GameIdProvider;
import game.NumberGateway;

public class CreateGameInteractor implements CreateGameUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;
    private final GameIdProvider gameIdProvider;

    public CreateGameInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository, GameIdProvider gameIdProvider) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
        this.gameIdProvider = gameIdProvider;
    }

    @Override
    public int createGameAndReturnGameId() {
        int id = gameIdProvider.getNextId();
        int generatedNumber = gateway.generateNumber();
        gameEntityRepository.save(new GameEntity(id, 0, generatedNumber));
        return id;
    }
}
