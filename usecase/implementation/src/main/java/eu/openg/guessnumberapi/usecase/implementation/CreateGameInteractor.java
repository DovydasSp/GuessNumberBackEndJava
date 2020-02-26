package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.domain.Game;
import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;

public class CreateGameInteractor implements CreateGameUseCase {
    private final NumberGateway gateway;
    private final GameRepository gameRepository;

    public CreateGameInteractor(NumberGateway gateway, GameRepository gameRepository) {
        this.gateway = gateway;
        this.gameRepository = gameRepository;
    }

    @Override
    public int createGameAndReturnGameId() {
        int actualNumber = gateway.generateNumber();
        return gameRepository.saveNewGameAndReturnId(new Game(actualNumber));
    }
}
