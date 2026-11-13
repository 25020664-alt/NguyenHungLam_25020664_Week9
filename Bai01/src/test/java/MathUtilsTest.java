import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathUtilsTest {

    @Test
    void testSum() {
        MathUtils math = new MathUtils();
        int result = math.sum(4, 3);
        assertEquals(7, result);
    }

    @Test
    void testSumWithNegativeNumbers() {
        MathUtils math = new MathUtils();
        int result = math.sum(-4, 3);
        assertEquals(-1, result);
    }
}