import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class MathUtils {
    public int sum(int a, int b) {
        return a + b;
    }
}
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        MathUtils m = new MathUtils();
        logger.info("Sum = {}", m.sum(4, 3));
    }
}

