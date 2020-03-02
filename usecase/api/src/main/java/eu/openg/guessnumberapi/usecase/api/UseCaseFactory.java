package eu.openg.guessnumberapi.usecase.api;

public interface UseCaseFactory {
    CreateGameUseCase buildCreateGameUseCase();

    GuessNumberUseCase buildGuessNumberUseCase();

    GetGamesUseCase buildGetGamesUseCase();
}
