package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.GameEntity;
import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;

public class CreateGameInteractor implements CreateGameUseCase {
    private final NumberGateway gateway;
    private final GameEntityRepository gameEntityRepository;

    public CreateGameInteractor(NumberGateway gateway, GameEntityRepository gameEntityRepository) {
        this.gateway = gateway;
        this.gameEntityRepository = gameEntityRepository;
    }

    @Override
    public int createGameAndReturnGameId() {
        int generatedNumber = gateway.generateNumber();
        return gameEntityRepository.save(new GameEntity(0, 0, generatedNumber));
    }
}
