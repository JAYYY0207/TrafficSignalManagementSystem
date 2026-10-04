package threads;

import analytics.CongestionAnalyzer;
import analytics.Logger;
import model.Intersection;

import java.util.ArrayList;

public class AnalyticsThread extends Thread {

    private Intersection intersection;
    private CongestionAnalyzer congestionAnalyzer;
    private volatile boolean running = true;

    public AnalyticsThread(Intersection intersection) {
        this.intersection = intersection;
        this.congestionAnalyzer = new CongestionAnalyzer();
    }

    @Override
    public void run() {
        while (running) {
            String level = congestionAnalyzer.getCongestionLevel(
                    new ArrayList<>(intersection.getAllLanes().values()));
            Logger.info("Current congestion level: " + level);

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stopAnalytics() {
        running = false;
        this.interrupt();
    }
}
