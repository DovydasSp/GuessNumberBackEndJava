import game.GameEntity;
import game.GameEntityRepository;
import game.IIdProvider;
import game.NumberGateway;

public class CreateGameInteractor implements CreateGameUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;
    private final IIdProvider idProvider;

    public CreateGameInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository, IIdProvider idProvider) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
        this.idProvider = idProvider;
    }

    @Override
    public int createGameAndReturnGameId() {
        int id = idProvider.getNextId();
        int generatedNumber = gateway.generateNumber();
        gameEntityRepository.save(new GameEntity(id, 0, generatedNumber));
        return id;
    }
}
