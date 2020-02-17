package game;

public class FakeGameIdProvider implements GameIdProvider {
    @Override
    public int getNextId() {
        return 0;
    }
}
