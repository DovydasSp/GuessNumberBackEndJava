public class GameInteractor implements GameUseCase {
    private final GameGatewayInterface gateway;

    public GameInteractor(GameGatewayInterface gateway) {
        this.gateway = gateway;
    }

}
