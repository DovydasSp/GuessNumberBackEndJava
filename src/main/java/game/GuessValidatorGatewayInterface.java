package game;

public interface GuessValidatorGatewayInterface {
    boolean isGuessCorrect(int guessedNumber, int generatedNumber);

    boolean isGuessBiggerThanGenerated(int guessedNumber, int generatedNumber);

    boolean isGuessNumberInBoundaries(int guessedNumber);
}
