public class Interactor implements InteractorInterface {
    private GatewayInterface gateway;

    public Interactor(GatewayInterface gateway) {
        this.gateway = gateway;
    }

}
