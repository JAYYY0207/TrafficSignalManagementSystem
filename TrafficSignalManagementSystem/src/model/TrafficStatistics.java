package model;

public class TrafficStatistics {

    private int totalVehiclesProcessed;
    private double averageWaitTimeSeconds;
    private int maxQueueLength;
    private int emergencyOverrideCount;

    public TrafficStatistics() {
        this.totalVehiclesProcessed = 0;
        this.averageWaitTimeSeconds = 0.0;
        this.maxQueueLength = 0;
        this.emergencyOverrideCount = 0;
    }

    public int getTotalVehiclesProcessed() {
        return totalVehiclesProcessed;
    }

    public void incrementVehiclesProcessed() {
        this.totalVehiclesProcessed++;
    }

    public double getAverageWaitTimeSeconds() {
        return averageWaitTimeSeconds;
    }

    public void setAverageWaitTimeSeconds(double averageWaitTimeSeconds) {
        this.averageWaitTimeSeconds = averageWaitTimeSeconds;
    }

    public int getMaxQueueLength() {
        return maxQueueLength;
    }

    public void updateMaxQueueLength(int currentLength) {
        if (currentLength > this.maxQueueLength) {
            this.maxQueueLength = currentLength;
        }
    }

    public int getEmergencyOverrideCount() {
        return emergencyOverrideCount;
    }

    public void incrementEmergencyOverrides() {
        this.emergencyOverrideCount++;
    }

    @Override
    public String toString() {
        return "Stats[processed=" + totalVehiclesProcessed
                + ", avgWait=" + averageWaitTimeSeconds
                + "s, maxQueue=" + maxQueueLength
                + ", emergencyOverrides=" + emergencyOverrideCount + "]";
    }
}
