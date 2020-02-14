import game.GameEntityRepository;
import game.NumberGateway;

public class CreateGameInteractor implements CreateGameUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;

    public CreateGameInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public int createGameAndReturnGameId() {
        return 0;
    }
}
