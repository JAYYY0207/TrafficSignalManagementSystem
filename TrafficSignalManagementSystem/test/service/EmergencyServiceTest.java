package service;

import enums.Direction;
import enums.VehicleCategory;
import model.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmergencyServiceTest {

    @Test
    public void testEmergencyOverrideTriggers() throws Exception {
        Intersection intersection = new Intersection("I1");
        Lane lane = new Lane("L1", Direction.NORTH);
        lane.addVehicle(new Vehicle("V1", VehicleCategory.AMBULANCE, Direction.NORTH, 0, 40));
        intersection.addLane(lane);
        intersection.addSignal(new TrafficSignal("S1", Direction.NORTH));

        SignalService signalService = new SignalService();
        EmergencyService emergencyService = new EmergencyService(signalService);
        TrafficStatistics stats = new TrafficStatistics();

        boolean overridden = emergencyService.checkAndOverride(intersection, stats);

        assertTrue(overridden);
        assertEquals(1, stats.getEmergencyOverrideCount());
    }
}