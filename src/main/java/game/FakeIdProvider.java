package game;

public class FakeIdProvider implements IIdProvider {
    @Override
    public int getNextId() {
        return 0;
    }
}
