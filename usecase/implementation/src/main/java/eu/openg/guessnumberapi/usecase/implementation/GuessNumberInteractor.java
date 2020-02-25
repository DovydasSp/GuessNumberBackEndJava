package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;

import java.util.Optional;

public class GuessNumberInteractor implements GuessNumberUseCase {

    private final GuessValidator gateway;
    private final GameRepository gameRepository;

    public GuessNumberInteractor(GuessValidator gateway, GameRepository gameRepository) {
        this.gateway = gateway;
        this.gameRepository = gameRepository;
    }

    @Override
    public BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber) {
        return Optional.of(gameRepository)
                .map(gameRepo -> gameRepo.fetchGame(gameId))
                .map(game -> checkGuessAndReturnBoundaryGuessResponse(guessNumber, game))
                .orElse(null);
    }

    private BoundaryGuessResponse checkGuessAndReturnBoundaryGuessResponse(int guessNumber, Game game) {
        BoundaryGuessResultStatus status = gateway.checkGuessAndReturnBoundaryGuessResponse(guessNumber, game.getActualNumber());
        int numberOfGuesses = gameRepository.incrementAndReturnGuessCount(game.getGameId());
        return new BoundaryGuessResponse(status, numberOfGuesses);
    }
}
