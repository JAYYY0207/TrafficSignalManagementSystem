package analytics;

import model.Lane;
import java.util.List;

public class CongestionAnalyzer {

    public String getCongestionLevel(List<Lane> lanes) {
        int totalVehicles = 0;
        for (Lane lane : lanes) {
            totalVehicles += lane.getQueueLength();
        }

        double avgPerLane = lanes.isEmpty() ? 0 : (double) totalVehicles / lanes.size();

        if (avgPerLane >= utils.Constants.HIGH_DENSITY_THRESHOLD) {
            return "SEVERE";
        } else if (avgPerLane >= utils.Constants.MEDIUM_DENSITY_THRESHOLD) {
            return "MODERATE";
        } else {
            return "LIGHT";
        }
    }
}