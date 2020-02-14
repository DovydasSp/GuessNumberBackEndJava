import game.GameEntityRepository;
import game.GuessValidator;

public class GuessNumberInteractor implements GuessNumberUseCase {
    private final GuessValidator gateway;
    private final GameEntityRepository gameEntityRepository;

    public GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public void execute(String s) {

    }
}
