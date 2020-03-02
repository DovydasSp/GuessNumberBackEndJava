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
        List<Game> games = gameRepository.fetchGames();
        return convert(games);
    }

    private List<BoundaryGame> convert(List<Game> games) {
        return games.stream().map(game -> new BoundaryGame(game.getGameId(), game.getGuessCount(), game.getActualNumber()))
                .collect(Collectors.toList());
    }
}
