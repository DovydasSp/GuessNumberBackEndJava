public class GameEntity {
    private int guessCount;
    private int generatedNumber;

    public GameEntity(){
        guessCount = 0;
        generatedNumber = 0;
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
