package game;

import java.util.Random;

public class RandomNumberGateway implements NumberGateway {
    public int generateNumber(){
        return new Random().nextInt(Constants.MAX_NUMBER_TO_GUESS) + 1;
    }
}
