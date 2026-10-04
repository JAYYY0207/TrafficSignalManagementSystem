package controller;

import enums.Direction;
import model.Intersection;
import model.Lane;
import repository.VehicleRepository;
import service.TrafficService;
import service.VehicleService;
import org.junit.Test;
import static org.junit.Assert.*;

public class TrafficControllerTest {

    @Test
    public void testGetWaitingVehicleCount() {
        Intersection intersection = new Intersection("I1");
        Lane lane = new Lane("L1", Direction.NORTH);
        intersection.addLane(lane);

        VehicleService vehicleService = new VehicleService(new VehicleRepository());
        vehicleService.spawnVehicle(lane);

        TrafficService trafficService = new TrafficService(vehicleService);
        TrafficController controller = new TrafficController(trafficService);

        assertEquals(1, controller.getWaitingVehicleCount(intersection));
    }
}