package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;

public class GuessNumberInteractor implements GuessNumberUseCase {

    GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
    }

    @Override
    public BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber) {
        //TODO complete
        throw new UnsupportedOperationException();
    }
}
