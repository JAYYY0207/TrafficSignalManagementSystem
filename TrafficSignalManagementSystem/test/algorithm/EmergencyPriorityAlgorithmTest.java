package algorithm;

import enums.Direction;
import enums.VehicleCategory;
import model.Intersection;
import model.Lane;
import model.Vehicle;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmergencyPriorityAlgorithmTest {

    @Test
    public void testFindsEmergencyDirection() {
        Intersection intersection = new Intersection("I1");
        Lane lane = new Lane("L1", Direction.EAST);
        lane.addVehicle(new Vehicle("V1", VehicleCategory.AMBULANCE, Direction.EAST, 0, 40));
        intersection.addLane(lane);

        Direction result = EmergencyPriorityAlgorithm.findEmergencyDirection(intersection);
        assertEquals(Direction.EAST, result);
    }
}