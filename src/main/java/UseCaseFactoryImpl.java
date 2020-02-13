public class UseCaseFactoryImpl implements UseCaseFactory {

    public GameUseCase buildInteractor() {
        GameGatewayInterface gateway = new GameGameGateway();
        return new GameInteractor(gateway);
    }
}
