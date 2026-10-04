package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.Vehicle;

public class VehicleRepository {

    private List<Vehicle> activeVehicles;

    public VehicleRepository() {
        this.activeVehicles = new ArrayList<>();
    }

    public void add(Vehicle vehicle) {
        activeVehicles.add(vehicle);
    }

    public void remove(String vehicleId) {
        activeVehicles.removeIf(v -> v.getId().equals(vehicleId));
    }

    public Optional<Vehicle> findById(String vehicleId) {
        return activeVehicles.stream()
                .filter(v -> v.getId().equals(vehicleId))
                .findFirst();
    }

    public List<Vehicle> getAll() {
        return activeVehicles;
    }

    public int count() {
        return activeVehicles.size();
    }
}
