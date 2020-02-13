package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {
    private NumberGeneratorGateway numberGenerator;

    @BeforeEach
    void setUp() {
        numberGenerator = new NumberGeneratorGateway();
    }

    @Test
    void generateNumber() {
        for (int i = 0; i < 1000; i++) {
            int generatedNumber = numberGenerator.generateNumber();
            assertTrue(generatedNumber >= 1 && generatedNumber <= 10);
        }
    }
}
