package service;

import enums.Direction;
import model.Lane;
import model.Vehicle;
import repository.VehicleRepository;
import org.junit.Test;
import static org.junit.Assert.*;

public class VehicleServiceTest {

    @Test
    public void testSpawnVehicleAddsToLane() {
        VehicleRepository repo = new VehicleRepository();
        VehicleService service = new VehicleService(repo);
        Lane lane = new Lane("L1", Direction.NORTH);

        Vehicle v = service.spawnVehicle(lane);

        assertEquals(1, lane.getQueueLength());
        assertEquals(1, repo.count());
        assertNotNull(v);
    }
}
