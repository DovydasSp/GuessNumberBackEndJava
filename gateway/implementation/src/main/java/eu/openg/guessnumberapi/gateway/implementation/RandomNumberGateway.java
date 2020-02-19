package eu.openg.guessnumberapi.gateway.implementation;

import eu.openg.guessnumberapi.gateway.api.NumberGateway;

import java.util.Random;

public class RandomNumberGateway implements NumberGateway {
    private static final int MAX_NUMBER_TO_GUESS = 10;

    public int generateNumber(){
        return new Random().nextInt(MAX_NUMBER_TO_GUESS) + 1;
    }
}
