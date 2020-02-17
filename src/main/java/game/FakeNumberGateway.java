package game;

public class FakeNumberGateway implements NumberGateway {
    @Override
    public int generateNumber() {
        return 3;
    }
}
