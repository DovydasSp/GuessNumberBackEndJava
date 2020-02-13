import game.NumberGeneratorGatewayInterface;

public class GenerateNumberInteractor implements GenerateNumberUseCase {
    private final NumberGeneratorGatewayInterface gateway;

    public GenerateNumberInteractor(NumberGeneratorGatewayInterface gateway) {
        this.gateway = gateway;
    }
}
