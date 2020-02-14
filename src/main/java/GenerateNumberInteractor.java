import game.GameEntity;
import game.GameEntityRepository;
import game.NumberGateway;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GenerateNumberInteractor implements GenerateNumberUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;

    public GenerateNumberInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public GameEntity execute() {
        throw new NotImplementedException();
    }
}
