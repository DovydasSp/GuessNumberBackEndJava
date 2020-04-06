package eu.openg.guessnumberapi.usecase.api;

import java.util.List;

public interface GetGamesUseCase {
    List<BoundaryGame> fetchGames();
}
