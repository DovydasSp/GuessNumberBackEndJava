package eu.openg.guessnumberapi.gateway.implementation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {
    private RandomNumberGateway numberGenerator;

    @BeforeEach
    void setUp() {
        numberGenerator = new RandomNumberGateway();
    }

    @Test
    void generateNumberDoesNotGoOutOfBounds() {
        for (int i = 0; i < 1000; i++) {
            int actualNumber = numberGenerator.generateNumber();
            assertTrue(actualNumber >= 1 && actualNumber <= 10);
        }
    }
}
