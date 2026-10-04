package gui;

import gui.DashboardFrame;
import model.Intersection;
import service.SimulationService;

public class DashboardController {

    private DashboardFrame dashboardFrame;

    public DashboardController(SimulationService simulationService) {
        Intersection intersection = simulationService.getIntersection();
        this.dashboardFrame = new DashboardFrame(intersection, simulationService.getStatistics());
    }

    public void openDashboard() {
        dashboardFrame.setVisible(true);
    }

    public void closeDashboard() {
        dashboardFrame.stopRefreshing();
        dashboardFrame.dispose();
    }
}
