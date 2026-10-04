package analytics;

import model.Vehicle;
import java.util.List;

public class WaitingTimeAnalyzer {

    public double calculateAverageWaitTime(List<Vehicle> vehicles, long currentTimeMillis) {
        if (vehicles.isEmpty()) return 0.0;

        double totalWait = 0;
        for (Vehicle v : vehicles) {
            totalWait += (currentTimeMillis - v.getArrivalTime()) / 1000.0;
        }
        return totalWait / vehicles.size();
    }

    public double calculateMaxWaitTime(List<Vehicle> vehicles, long currentTimeMillis) {
        double max = 0;
        for (Vehicle v : vehicles) {
            double wait = (currentTimeMillis - v.getArrivalTime()) / 1000.0;
            if (wait > max) max = wait;
        }
        return max;
    }
}