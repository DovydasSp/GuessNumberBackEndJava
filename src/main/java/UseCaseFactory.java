public interface UseCaseFactory {
    GenerateNumberUseCase buildGenerateNumberInteractor();

    GuessNumberUseCase buildGuessNumberInteractor();
}
