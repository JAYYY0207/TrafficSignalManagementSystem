package model;

import enums.VehicleCategory;
import enums.Direction;
import org.junit.Test;
import static org.junit.Assert.*;

public class VehicleTest {

    @Test
    public void testEmergencyVehicleDetection() {
        Vehicle ambulance = new Vehicle("V1", VehicleCategory.AMBULANCE, Direction.NORTH, 0, 40);
        assertTrue(ambulance.isEmergencyVehicle());

        Vehicle car = new Vehicle("V2", VehicleCategory.CAR, Direction.NORTH, 0, 40);
        assertFalse(car.isEmergencyVehicle());
    }
}