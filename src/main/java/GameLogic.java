import java.util.Random;

public class GameLogic {
    private int guessCount;
    private int generatedNumber;

    public GameLogic(){
        guessCount = 0;
        generatedNumber = 0;
    }

    public int generateNumber(){
        Random randomNumberGenerator = new Random();
        generatedNumber = randomNumberGenerator.nextInt(Constants.MAX_NUMBER_TO_GUESS);
        return generatedNumber;
    }

    public boolean isGuessCorrect(int guessedNumber){
        guessCount++;
        return guessedNumber == generatedNumber;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber){
        return guessedNumber > generatedNumber;
    }

    public void setGuessCount(int guessCount){ this.guessCount = guessCount; }

    public void setGeneratedNumber(int generatedNumber) { this.generatedNumber = generatedNumber; }

    public int returnGuessCount(){
        return guessCount;
    }

    public int returnGeneratedNumber(){
        return generatedNumber;
    }
}
