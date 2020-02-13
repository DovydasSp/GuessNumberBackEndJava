public class UseCaseFactoryImpl implements UseCaseFactory {

    public GameUseCase buildInteractor() {
        GameGatewayInterface gateway = new GameGateway();
        return new GameInteractor(gateway);
    }
}
