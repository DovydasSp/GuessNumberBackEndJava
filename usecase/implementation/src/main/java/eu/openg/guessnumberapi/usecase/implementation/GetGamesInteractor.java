package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;
import eu.openg.guessnumberapi.usecase.api.GetGamesUseCase;

import java.util.List;
import java.util.stream.Collectors;

public class GetGamesInteractor implements GetGamesUseCase {
    private final GameRepository gameRepository;

    public GetGamesInteractor(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<BoundaryGame> fetchGames() {
        return convert(gameRepository.fetchGames());
    }

    private List<BoundaryGame> convert(List<Game> games) {
        return games.stream()
                .map(this::gameToBoundaryGame)
                .collect(Collectors.toList());
    }

    private BoundaryGame gameToBoundaryGame(Game game) {
        return new BoundaryGame(game.getGameId(), game.getGuessCount(), game.getActualNumber());
    }
}
