package model;

import java.util.LinkedList;
import java.util.Queue;
import enums.Direction;

public class Lane {

    private String laneId;
    private Direction direction;
    private Queue<Vehicle> vehicleQueue;

    public Lane(String laneId, Direction direction) {
        this.laneId = laneId;
        this.direction = direction;
        this.vehicleQueue = new LinkedList<>();
    }

    public String getLaneId() {
        return laneId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleQueue.offer(vehicle);
    }

    public Vehicle removeVehicle() {
        return vehicleQueue.poll();
    }

    public int getQueueLength() {
        return vehicleQueue.size();
    }

    public boolean hasEmergencyVehicle() {
        return vehicleQueue.stream().anyMatch(Vehicle::isEmergencyVehicle);
    }

    public Queue<Vehicle> getVehicleQueue() {
        return vehicleQueue;
    }

    @Override
    public String toString() {
        return "Lane[" + laneId + " | " + direction + " | queue=" + vehicleQueue.size() + "]";
    }
}