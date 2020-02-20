package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;

public class GuessNumberInteractor implements GuessNumberUseCase {

    private final GuessValidator gateway;
    private final GameEntityRepository gameEntityRepository;

    GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber) {
        //TODO complete
        throw new UnsupportedOperationException();
    }
}
