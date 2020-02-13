import game.NumberGateway;

public class GenerateNumberInteractor implements GenerateNumberUseCase {
    private final NumberGateway gateway;

    public GenerateNumberInteractor(NumberGateway gateway) {
        this.gateway = gateway;
    }
}
