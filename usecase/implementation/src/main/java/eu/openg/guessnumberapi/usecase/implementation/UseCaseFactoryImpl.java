package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.gateway.api.GameRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;
    private final GameRepository gameRepository;

    public UseCaseFactoryImpl(NumberGateway numberGateway,
                              GuessValidator guessValidator,
                              GameRepository gameRepository) {
        this.numberGateway = numberGateway;
        this.guessValidator = guessValidator;
        this.gameRepository = gameRepository;
    }

    public CreateGameUseCase buildCreateGameUseCase() {
        return new CreateGameInteractor(numberGateway, gameRepository);
    }

    public GuessNumberUseCase buildGuessNumberUseCase() {
        return new GuessNumberInteractor(guessValidator, gameRepository);
    }
}
