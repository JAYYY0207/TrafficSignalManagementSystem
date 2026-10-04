package algorithm;

import enums.Direction;
import model.Intersection;
import model.Lane;

import java.util.Map;

public class EmergencyPriorityAlgorithm {

    private EmergencyPriorityAlgorithm() {}

    /**
     * Returns the direction that has an emergency vehicle waiting,
     * or null if none exists.
     */
    public static Direction findEmergencyDirection(Intersection intersection) {
        Direction oldestEmergencyDirection = null;
        long oldestArrivalTime = Long.MAX_VALUE;

        for (Map.Entry<Direction, Lane> entry : intersection.getAllLanes().entrySet()) {
            Lane lane = entry.getValue();
            if (lane.hasEmergencyVehicle()) {
                for (model.Vehicle v : lane.getVehicleQueue()) {
                    if (v.isEmergencyVehicle() && v.getArrivalTime() < oldestArrivalTime) {
                        oldestArrivalTime = v.getArrivalTime();
                        oldestEmergencyDirection = entry.getKey();
                    }
                }
            }
        }
        return oldestEmergencyDirection;
    }

    public static boolean requiresOverride(Intersection intersection) {
        return findEmergencyDirection(intersection) != null;
    }
}