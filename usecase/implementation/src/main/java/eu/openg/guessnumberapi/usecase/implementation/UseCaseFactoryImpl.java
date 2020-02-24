package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.gateway.api.GameEntityRepository;
import eu.openg.guessnumberapi.gateway.api.NumberGateway;
import eu.openg.guessnumberapi.usecase.api.CreateGameUseCase;
import eu.openg.guessnumberapi.usecase.api.GuessNumberUseCase;
import eu.openg.guessnumberapi.usecase.api.UseCaseFactory;

public class UseCaseFactoryImpl implements UseCaseFactory {
    private final NumberGateway numberGateway;
    private final GuessValidator guessValidator;
    private final GameEntityRepository gameEntityRepository;

    public UseCaseFactoryImpl(NumberGateway numberGateway,
                              GuessValidator guessValidator,
                              GameEntityRepository gameEntityRepository) {
        this.numberGateway = numberGateway;
        this.guessValidator = guessValidator;
        this.gameEntityRepository = gameEntityRepository;
    }

    public CreateGameUseCase buildCreateGameUseCase() {
        return new CreateGameInteractor(numberGateway, gameEntityRepository);
    }

    public GuessNumberUseCase buildGuessNumberUseCase() {
        return new GuessNumberInteractor(guessValidator, gameEntityRepository);
    }
}
