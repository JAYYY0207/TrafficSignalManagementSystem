package algorithm;

import model.Lane;

public class DensityCalculator {

    private DensityCalculator() {}

    public static int calculateDensity(Lane lane) {
        return lane.getQueueLength();
    }

    public static String densityLevel(int density) {
        if (density >= utils.Constants.HIGH_DENSITY_THRESHOLD) {
            return "HIGH";
        } else if (density >= utils.Constants.MEDIUM_DENSITY_THRESHOLD) {
            return "MEDIUM";
        } else {
            return "LOW";
        }
    }
}
