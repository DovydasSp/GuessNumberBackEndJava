import game.GameEntityRepository;
import game.GuessValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GuessNumberInteractor implements GuessNumberUseCase {
    private final GuessValidator gateway;
    private final GameEntityRepository gameEntityRepository;

    public GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public void execute(String s) {
        throw new NotImplementedException();
    }
}
