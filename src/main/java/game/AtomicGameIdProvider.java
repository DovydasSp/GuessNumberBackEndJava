package game;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicGameIdProvider implements GameIdProvider {
    private static final AtomicInteger provider = new AtomicInteger(0);

    public int getNextId() {
        return provider.incrementAndGet();
    }
}