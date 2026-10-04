package algorithm;

import enums.Direction;
import model.Intersection;
import model.Lane;

import java.util.Map;

public class SignalScheduler {

    private SignalScheduler() {}

    /**
     * Chooses the direction with the highest vehicle density
     * to receive the next green signal.
     */
    public static Direction selectNextGreenDirection(Intersection intersection) {
        Direction bestDirection = null;
        int highestDensity = -1;

        for (Map.Entry<Direction, Lane> entry : intersection.getAllLanes().entrySet()) {
            int density = DensityCalculator.calculateDensity(entry.getValue());
            if (density > highestDensity) {
                highestDensity = density;
                bestDirection = entry.getKey();
            }
        }
        return bestDirection;
    }

    public static int calculateGreenDuration(int density) {
        if (density >= utils.Constants.HIGH_DENSITY_THRESHOLD) {
            return utils.Constants.DEFAULT_GREEN_DURATION + 10;
        } else if (density >= utils.Constants.MEDIUM_DENSITY_THRESHOLD) {
            return utils.Constants.DEFAULT_GREEN_DURATION;
        } else {
            return utils.Constants.DEFAULT_GREEN_DURATION - 5;
        }
    }
}