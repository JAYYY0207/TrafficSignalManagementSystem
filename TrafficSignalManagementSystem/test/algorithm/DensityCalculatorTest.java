package algorithm;

import enums.Direction;
import enums.VehicleCategory;
import model.Lane;
import model.Vehicle;
import org.junit.Test;
import static org.junit.Assert.*;

public class DensityCalculatorTest {

    @Test
    public void testDensityMatchesQueueLength() {
        Lane lane = new Lane("L1", Direction.NORTH);
        lane.addVehicle(new Vehicle("V1", VehicleCategory.CAR, Direction.NORTH, 0, 40));
        lane.addVehicle(new Vehicle("V2", VehicleCategory.CAR, Direction.NORTH, 0, 40));

        assertEquals(2, DensityCalculator.calculateDensity(lane));
    }
}