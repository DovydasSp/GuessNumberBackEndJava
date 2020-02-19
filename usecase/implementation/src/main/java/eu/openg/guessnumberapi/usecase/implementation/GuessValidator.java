package eu.openg.guessnumberapi.usecase.implementation;

public class GuessValidator {
    public boolean isGuessCorrect(int guessedNumber, int generatedNumber){
        return guessedNumber == generatedNumber;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber, int generatedNumber){
        return guessedNumber > generatedNumber;
    }

    public boolean isGuessNumberInBoundaries(int guessedNumber){
//        return guessedNumber >= 1 && guessedNumber <= Constants.MAX_NUMBER_TO_GUESS;
        return true; //TODO validate only in gw or uc + test
    }
}