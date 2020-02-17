public interface UseCaseFactory {
    CreateGameUseCase buildCreateGameUseCase();

    GuessNumberUseCase buildGuessNumberUseCase();
}
