package game;

public class GameEntity {
    private final int guessCount;
    private final int generatedNumber;

    public GameEntity(int guessCount, int generatedNumber) {
        this.guessCount = guessCount;
        this.generatedNumber = generatedNumber;
    }

    public int returnGuessCount(){
        return guessCount;
    }

    public int returnGeneratedNumber(){
        return generatedNumber;
    }
}
