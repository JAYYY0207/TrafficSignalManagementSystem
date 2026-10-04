package service;

import enums.Direction;
import model.Intersection;
import model.Lane;
import model.Vehicle;

public class TrafficService {

    private VehicleService vehicleService;

    public TrafficService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Moves one vehicle through the intersection for the currently
     * green-signaled direction.
     */
    public Vehicle processGreenDirection(Intersection intersection, Direction direction) {
        Lane lane = intersection.getLane(direction);
        if (lane == null || lane.getQueueLength() == 0) {
            return null;
        }
        return vehicleService.processNextVehicle(lane);
    }

    public int getTotalVehiclesWaiting(Intersection intersection) {
        int total = 0;
        for (Lane lane : intersection.getAllLanes().values()) {
            total += lane.getQueueLength();
        }
        return total;
    }
}