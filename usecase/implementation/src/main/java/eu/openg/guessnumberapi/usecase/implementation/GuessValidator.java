package eu.openg.guessnumberapi.usecase.implementation;

public class GuessValidator {
    public boolean isGuessCorrect(int guessedNumber, int generatedNumber){
        return guessedNumber == generatedNumber;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber, int generatedNumber){
        return guessedNumber > generatedNumber;
    }
}
