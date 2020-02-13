package game;

public class GuessValidatorGateway implements GuessValidatorGatewayInterface {
    public boolean isGuessCorrect(int guessedNumber, int generatedNumber){
        return guessedNumber == generatedNumber;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber, int generatedNumber){
        return guessedNumber > generatedNumber;
    }

    public boolean isGuessNumberInBoundaries(int guessedNumber){
        return guessedNumber >= 1 && guessedNumber <= Constants.MAX_NUMBER_TO_GUESS;
    }
}
