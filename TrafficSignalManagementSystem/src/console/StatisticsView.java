package console;

import analytics.ReportGenerator;
import model.TrafficStatistics;

public class StatisticsView {

    private ReportGenerator reportGenerator;

    public StatisticsView() {
        this.reportGenerator = new ReportGenerator();
    }

    public void printStatistics(TrafficStatistics stats) {
        System.out.println(reportGenerator.generateSummary(stats));
    }
}