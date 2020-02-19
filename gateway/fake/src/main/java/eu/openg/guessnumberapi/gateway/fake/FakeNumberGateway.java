package eu.openg.guessnumberapi.gateway.fake;

import eu.openg.guessnumberapi.gateway.api.NumberGateway;

public class FakeNumberGateway implements NumberGateway {
    @Override
    public int generateNumber() {
        return 3;
    }
}
