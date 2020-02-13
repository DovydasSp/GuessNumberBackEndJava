import game.NumberGateway;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GenerateNumberInteractor implements GenerateNumberUseCase {
    private final NumberGateway gateway;

    public GenerateNumberInteractor(NumberGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void execute() {
        throw new NotImplementedException();
    }
}
