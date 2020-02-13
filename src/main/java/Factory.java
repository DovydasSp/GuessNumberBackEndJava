public class Factory implements FactoryInterface {

    public InteractorInterface buildInteractor() {
        GatewayInterface gateway = new Gateway();
        return new Interactor(gateway);
    }
}
