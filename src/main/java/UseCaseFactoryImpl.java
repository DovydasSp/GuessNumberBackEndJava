import game.GuessValidatorGateway;
import game.GuessValidatorGatewayInterface;
import game.NumberGeneratorGateway;
import game.NumberGeneratorGatewayInterface;

public class UseCaseFactoryImpl implements UseCaseFactory {

    public GenerateNumberUseCase buildGenerateNumberInteractor() {
        NumberGeneratorGatewayInterface gateway = new NumberGeneratorGateway();
        return new GenerateNumberInteractor(gateway);
    }

    public GuessNumberUseCase buildGuessNumberInteractor() {
        GuessValidatorGatewayInterface gateway = new GuessValidatorGateway();
        return new GuessNumberInteractor(gateway);

    }
}
