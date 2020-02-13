import game.GuessValidator;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GuessNumberInteractor implements GuessNumberUseCase {
    private final GuessValidator gateway;

    public GuessNumberInteractor(GuessValidator gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute(String s) {
        throw new NotImplementedException();
    }
}
