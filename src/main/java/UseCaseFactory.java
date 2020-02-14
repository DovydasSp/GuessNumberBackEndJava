public interface UseCaseFactory {
    CreateGameUseCase buildGenerateNumberInteractor();

    GuessNumberUseCase buildGuessNumberInteractor();
}
