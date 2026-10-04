package repository;

import java.util.ArrayList;
import java.util.List;
import model.TrafficStatistics;

public class ReportRepository {

    private List<TrafficStatistics> savedReports;

    public ReportRepository() {
        this.savedReports = new ArrayList<>();
    }

    public void saveReport(TrafficStatistics stats) {
        savedReports.add(stats);
    }

    public List<TrafficStatistics> getAllReports() {
        return savedReports;
    }

    public TrafficStatistics getLatestReport() {
        if (savedReports.isEmpty()) {
            return null;
        }
        return savedReports.get(savedReports.size() - 1);
    }
}