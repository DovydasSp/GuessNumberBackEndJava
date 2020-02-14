public interface UseCaseFactory {
    CreateGameUseCase buildCreateGameInteractor();

    GuessNumberUseCase buildGuessNumberInteractor();
}
