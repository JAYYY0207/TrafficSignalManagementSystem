package threads;

import enums.Direction;
import model.Intersection;
import model.Lane;
import service.VehicleService;
import utils.Constants;
import utils.RandomUtils;

public class VehicleGeneratorThread extends Thread {

    private Intersection intersection;
    private VehicleService vehicleService;
    private volatile boolean running = true;

    public VehicleGeneratorThread(Intersection intersection, VehicleService vehicleService) {
        this.intersection = intersection;
        this.vehicleService = vehicleService;
    }

    @Override
    public void run() {
        while (running) {
            Direction direction = RandomUtils.randomDirection();
            Lane lane = intersection.getLane(direction);
            if (lane != null) {
                vehicleService.spawnVehicle(lane);
            }
            try {
                Thread.sleep(Constants.VEHICLE_SPAWN_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stopGenerating() {
        running = false;
        this.interrupt();
    }
}
