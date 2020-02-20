package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
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
        BoundaryGuessResponse response = null;
        GameEntity gameEntity = gameEntityRepository.fetchGameEntity(gameId);
        int generatedNumber = gameEntity.returnGeneratedNumber();
        int guessCount = gameEntity.returnGuessCount() + 1;
        if (gateway.isGuessCorrect(guessNumber, generatedNumber)) {
            response = saveNewGameEntityAndReturnBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    "Correct");
        } else if (gateway.isGuessBiggerThanGenerated(guessNumber, generatedNumber)) {
            response = saveNewGameEntityAndReturnBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    "Higher");
        } else {
            response = saveNewGameEntityAndReturnBoundaryGuessResponse(gameId, guessCount, generatedNumber,
                    "Lower");
        }
        return response;
    }

    private BoundaryGuessResponse saveNewGameEntityAndReturnBoundaryGuessResponse(int gameId, int guessCount,
                                                                                  int generatedNumber, String message) {
        GameEntity changedGameEntity = new GameEntity(gameId, guessCount, generatedNumber);
        gameEntityRepository.save(changedGameEntity);
        return new BoundaryGuessResponse(message, guessCount);
    }
}
