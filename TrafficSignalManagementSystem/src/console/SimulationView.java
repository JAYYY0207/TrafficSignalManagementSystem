package console;

import model.TrafficStatistics;

public class SimulationView {

    public void printTickSummary(int tickNumber, TrafficStatistics stats) {
        System.out.println("[Tick " + tickNumber + "] Processed so far: "
                + stats.getTotalVehiclesProcessed()
                + " | Max queue seen: " + stats.getMaxQueueLength());
    }
}