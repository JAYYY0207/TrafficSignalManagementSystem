package model;

import enums.VehicleCategory;

public class VehicleType {

    private VehicleCategory category;
    private double averageSpeed;
    private int priorityLevel; // higher = more urgent (ambulance = highest)
    private double lengthInMeters;

    public VehicleType(VehicleCategory category, double averageSpeed, int priorityLevel, double lengthInMeters) {
        this.category = category;
        this.averageSpeed = averageSpeed;
        this.priorityLevel = priorityLevel;
        this.lengthInMeters = lengthInMeters;
    }

    public VehicleCategory getCategory() {
        return category;
    }

    public double getAverageSpeed() {
        return averageSpeed;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public double getLengthInMeters() {
        return lengthInMeters;
    }

    @Override
    public String toString() {
        return category + " (speed=" + averageSpeed + ", priority=" + priorityLevel + ")";
    }
}