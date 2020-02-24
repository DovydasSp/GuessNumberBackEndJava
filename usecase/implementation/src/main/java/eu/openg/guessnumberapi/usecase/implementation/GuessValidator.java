package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;

public class GuessValidator {
    public boolean isGuessCorrect(int guessedNumber, int generatedNumber){
        return guessedNumber == generatedNumber;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber, int generatedNumber){
        return guessedNumber > generatedNumber;
    }

    public BoundaryGuessResultStatus checkGuessAndReturnBoundaryGuessResponse(int guessNumber, int generatedNumber) {
        if (isGuessCorrect(guessNumber, generatedNumber))
            return BoundaryGuessResultStatus.CORRECT;
        if (isGuessBiggerThanGenerated(guessNumber, generatedNumber))
            return BoundaryGuessResultStatus.LESS;
        return BoundaryGuessResultStatus.MORE;
    }
}
