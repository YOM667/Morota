package me.youm.morota.utils.math;

import java.security.SecureRandom;
import java.util.Random;

/**
 * @author YouM
 * Created on 2024/2/2
 */
public class RandomUtil {
    public static float getRandomInRange(float min, float max) {
        SecureRandom random = new SecureRandom();
        return random.nextFloat() * (max - min) + min;
    }

    public static double getRandomInRange(double min, double max) {
        SecureRandom random = new SecureRandom();
        return random.nextDouble() * (max - min) + min;
    }
    public static int getRandomInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }
}
