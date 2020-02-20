package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResponse;
import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;

import static java.util.Objects.isNull;

public class GuessNumberInteractor implements GuessNumberUseCase {

    private final GuessValidator gateway;
    private final GameEntityRepository gameEntityRepository;

    public GuessNumberInteractor(GuessValidator gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public BoundaryGuessResponse checkGuessAndReturnResponse(int gameId, int guessNumber) {
        GameEntity gameEntity = gameEntityRepository.fetchGameEntity(gameId);
        if (isNull(gameEntity))
            return null;
        int generatedNumber = gameEntity.getGeneratedNumber();
        int guessCount = gameEntity.getGuessCount() + 1;
        return checkGuessAndReturnBoundaryGuessResponse(guessNumber, generatedNumber, gameId, guessCount);
    }

    private BoundaryGuessResponse checkGuessAndReturnBoundaryGuessResponse(int guessNumber, int generatedNumber,
                                                                           int gameId, int guessCount) {
        if (gateway.isGuessCorrect(guessNumber, generatedNumber)) {
            return saveNewGameEntityAndCreateBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    BoundaryGuessResultStatus.CORRECT);
        } else if (gateway.isGuessBiggerThanGenerated(guessNumber, generatedNumber)) {
            return saveNewGameEntityAndCreateBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    BoundaryGuessResultStatus.LESS);
        } else {
            return saveNewGameEntityAndCreateBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    BoundaryGuessResultStatus.MORE);
        }
    }

    private BoundaryGuessResponse saveNewGameEntityAndCreateBoundaryGuessResponse(int gameId, int guessCount,
                                                                                  int generatedNumber, BoundaryGuessResultStatus message) {
        GameEntity changedGameEntity = new GameEntity(gameId, guessCount, generatedNumber);
        gameEntityRepository.save(changedGameEntity);
        if (message != BoundaryGuessResultStatus.CORRECT)
            return new BoundaryGuessResponse(message, null);
        else
            return new BoundaryGuessResponse(null, guessCount);
    }
}
