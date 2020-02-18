package game;

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
            int generatedNumber = numberGenerator.generateNumber();
            assertTrue(generatedNumber >= 1 && generatedNumber <= 10);
        }
    }
}
