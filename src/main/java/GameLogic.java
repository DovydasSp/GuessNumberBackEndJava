public class GameLogic {
    private int guessCount;
    private int generatedNumber;

    public GameLogic(){
        guessCount = 0;
        generatedNumber = 0;
    }

    public int generateNumber(){

        return 1;
    }

    public boolean isGuessCorrect(int guessedNumber){

        return true;
    }

    public boolean isGuessBiggerThanGenerated(int guessedNumber){

        return true;
    }

    public int returnGuessCount(){
        return guessCount;
    }

    public int returnGeneratedNumber(){
        return generatedNumber;
    }
}
