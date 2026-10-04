package model;

import enums.VehicleCategory;
import enums.Direction;

public class Vehicle {

    private String id;
    private VehicleCategory category;
    private Direction direction;
    private long arrivalTime;
    private double speed;

    public Vehicle(String id, VehicleCategory category, Direction direction, long arrivalTime, double speed) {
        this.id = id;
        this.category = category;
        this.direction = direction;
        this.arrivalTime = arrivalTime;
        this.speed = speed;
    }

    public String getId() {
        return id;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public Direction getDirection() {
        return direction;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isEmergencyVehicle() {
        return category == VehicleCategory.AMBULANCE;
    }

    @Override
    public String toString() {
        return "[" + category + " | ID:" + id + " | Dir:" + direction + "]";
    }
}
