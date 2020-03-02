package eu.openg.guessnumberapi.rest.entity.converter;

import eu.openg.guessnumberapi.rest.entity.RestGame;
import eu.openg.guessnumberapi.usecase.api.BoundaryGame;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class GamesResponseConverter implements RestResponseConverter<List<BoundaryGame>, List<RestGame>> {
    public List<RestGame> convert(List<BoundaryGame> boundaryGames) {
        if (isNull(boundaryGames))
            return Collections.emptyList();
        return boundaryGames.stream().map(boundaryGame -> new RestGame(boundaryGame.getGameId(),
                boundaryGame.getGuessCount(), boundaryGame.getActualNumber())).collect(Collectors.toList());
    }
}
