import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NumberGeneratorTest {
    private NumberGenerator numberGenerator;

    @Before
    public void setUp(){
        numberGenerator = new NumberGenerator();
    }

    @Test
    public void generateNumber() {
        int firstGenerated = numberGenerator.generateNumber();
        int secondGenerated = numberGenerator.generateNumber();
        int thirdGenerated = numberGenerator.generateNumber();
        assertTrue(firstGenerated != secondGenerated || secondGenerated != thirdGenerated);
    }
}
