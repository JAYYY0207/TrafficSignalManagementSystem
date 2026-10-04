package service;

import enums.Direction;
import model.Lane;
import model.Vehicle;
import repository.VehicleRepository;
import utils.RandomUtils;

public class VehicleService {

    private VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle spawnVehicle(Lane lane) {
        String id = RandomUtils.generateVehicleId();
        Direction direction = lane.getDirection();
        double speed = RandomUtils.randomSpeed(20, 60);

        Vehicle vehicle = new Vehicle(id, RandomUtils.randomVehicleCategory(), direction,
                System.currentTimeMillis(), speed);

        lane.addVehicle(vehicle);
        vehicleRepository.add(vehicle);
        return vehicle;
    }

    public Vehicle processNextVehicle(Lane lane) {
        Vehicle vehicle = lane.removeVehicle();
        if (vehicle != null) {
            vehicleRepository.remove(vehicle.getId());
        }
        return vehicle;
    }
}