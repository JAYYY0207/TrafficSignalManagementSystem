package analytics;

import model.TrafficStatistics;

public class ReportGenerator {

    public String generateSummary(TrafficStatistics stats) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== SIMULATION REPORT =====\n");
        sb.append("Total Vehicles Processed : ").append(stats.getTotalVehiclesProcessed()).append("\n");
        sb.append("Average Wait Time (s)    : ").append(stats.getAverageWaitTimeSeconds()).append("\n");
        sb.append("Max Queue Length         : ").append(stats.getMaxQueueLength()).append("\n");
        sb.append("Emergency Overrides      : ").append(stats.getEmergencyOverrideCount()).append("\n");
        sb.append("==============================");
        return sb.toString();
    }
}