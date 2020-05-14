package eu.openg.guessnumberapi.usecase.implementation;

import eu.openg.guessnumberapi.usecase.api.BoundaryGuessResultStatus;

public class GuessValidator {
    private boolean isGuessCorrect(int guessedNumber, int actualNumber) {
        return guessedNumber == actualNumber;
    }

    private boolean isGuessBiggerThanGenerated(int guessedNumber, int actualNumber) {
        return guessedNumber > actualNumber;
    }

    public BoundaryGuessResultStatus checkGuessAndReturnBoundaryGuessResponse(int guessNumber, int actualNumber) {
        if (isGuessCorrect(guessNumber, actualNumber))
            return BoundaryGuessResultStatus.CORRECT;
        if (isGuessBiggerThanGenerated(guessNumber, actualNumber))
            return BoundaryGuessResultStatus.LESS;
        return BoundaryGuessResultStatus.MORE;
    }
}
