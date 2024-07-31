package island.util;

import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static boolean tryAction(double probability) {
        return random.nextDouble() < probability;
    }

    public static int getRandomInt(int bound) {
        return random.nextInt(bound);
    }
}
