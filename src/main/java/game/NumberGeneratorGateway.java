package game;

import java.util.Random;

public class NumberGeneratorGateway implements NumberGeneratorGatewayInterface {
    public int generateNumber(){
        return new Random().nextInt(Constants.MAX_NUMBER_TO_GUESS) + 1;
    }
}
