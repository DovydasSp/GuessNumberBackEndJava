import game.GameEntity;
import game.GameEntityRepository;
import game.NumberGateway;

public class GenerateNumberInteractor implements GenerateNumberUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;

    public GenerateNumberInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public GameEntity execute() {
        return null;
    }
}
