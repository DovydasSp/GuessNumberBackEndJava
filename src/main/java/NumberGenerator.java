import java.util.Random;

public class NumberGenerator {
    public int generateNumber(){
        return new Random().nextInt(Constants.MAX_NUMBER_TO_GUESS);
    }
}
