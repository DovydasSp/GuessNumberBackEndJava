package game;

import java.util.concurrent.atomic.AtomicInteger;

public class IdProvider implements IIdProvider {
    AtomicInteger provider = new AtomicInteger(0);

    public int getNextId() {
        return provider.incrementAndGet();
    }
}
