import game.GuessValidatorGatewayInterface;

public class GuessNumberInteractor implements GuessNumberUseCase {
    private final GuessValidatorGatewayInterface gateway;

    public GuessNumberInteractor(GuessValidatorGatewayInterface gateway) {
        this.gateway = gateway;
    }
}
