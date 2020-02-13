import game.GuessValidator;

public class GuessNumberInteractor implements GuessNumberUseCase {
    private final GuessValidator gateway;

    public GuessNumberInteractor(GuessValidator gateway) {
        this.gateway = gateway;
    }
}
