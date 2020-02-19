package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.gateway.api.GameIdProvider;

public class FakeGameIdProvider implements GameIdProvider {
    @Override
    public int getNextId() {
        return 0;
    }
}
