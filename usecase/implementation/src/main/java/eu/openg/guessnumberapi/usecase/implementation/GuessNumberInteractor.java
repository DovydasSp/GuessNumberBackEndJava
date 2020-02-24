package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;

import static java.util.Objects.isNull;

public class GuessNumberInteractor implements GuessNumberUseCase {

    private final GuessValidator gateway;
    private final GameRepository gameRepository;

    public GuessNumberInteractor(GuessValidator gateway, GameRepository gameRepository) {
        this.gateway = gateway;
        this.gameRepository = gameRepository;
    }

    @Override
    public BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber) {
        Game game = gameRepository.fetchGameEntity(gameId);
        if (isNull(game))
            return null;
        return checkGuessAndReturnBoundaryGuessResponse(guessNumber, game);
    }

    private BoundaryGuessResponse checkGuessAndReturnBoundaryGuessResponse(int guessNumber, Game game) {
        BoundaryGuessResultStatus status = gateway.checkGuessAndReturnBoundaryGuessResponse(guessNumber, game.getGeneratedNumber());
        int numberOfGuesses = gameRepository.incrementGuessCount(game);
        return new BoundaryGuessResponse(status, numberOfGuesses);
    }
}
