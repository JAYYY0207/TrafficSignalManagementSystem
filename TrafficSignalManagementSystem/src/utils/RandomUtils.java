package utils;

import java.util.Random;
import enums.VehicleCategory;
import enums.Direction;

public class RandomUtils {

    private static final Random random = new Random();

    private RandomUtils() {}

    public static VehicleCategory randomVehicleCategory() {
        // Ambulance should be rare
        int chance = random.nextInt(100);
        if (chance < 3) {
            return VehicleCategory.AMBULANCE;
        } else if (chance < 40) {
            return VehicleCategory.CAR;
        } else if (chance < 70) {
            return VehicleCategory.BIKE;
        } else {
            return VehicleCategory.BUS;
        }
    }

    public static Direction randomDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }

    public static double randomSpeed(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static int randomIntBetween(int min, int max) {
        return min + random.nextInt((max - min) + 1);
    }

    public static String generateVehicleId() {
        return "V-" + System.currentTimeMillis() + "-" + random.nextInt(1000);
    }
}
