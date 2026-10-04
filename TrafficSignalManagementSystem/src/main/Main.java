package main;

import console.ConsoleDisplay;
import console.ConsoleMenu;
import console.StatisticsView;
import controller.SignalController;
import controller.SimulationController;
import controller.TrafficController;
import gui.DashboardController;
import enums.Direction;
import model.Intersection;
import model.Lane;
import model.TrafficSignal;
import repository.VehicleRepository;
import service.*;
import threads.AnalyticsThread;
import threads.SignalTimerThread;
import threads.SimulationClock;
import threads.VehicleGeneratorThread;

public class Main {

    public static void main(String[] args) throws Exception {

        // Build intersection
        Intersection intersection = new Intersection("INT-1");
        for (Direction dir : Direction.values()) {
            intersection.addLane(new Lane(dir.name() + "-LANE", dir));
            intersection.addSignal(new TrafficSignal(dir.name() + "-SIGNAL", dir));
        }

        // Wire up services
        VehicleRepository vehicleRepository = new VehicleRepository();
        VehicleService vehicleService = new VehicleService(vehicleRepository);
        TrafficService trafficService = new TrafficService(vehicleService);
        SignalService signalService = new SignalService();
        EmergencyService emergencyService = new EmergencyService(signalService);
        PeakHourService peakHourService = new PeakHourService();

        SimulationService simulationService = new SimulationService(
                intersection, vehicleService, trafficService, signalService,
                emergencyService, peakHourService);
        DashboardController dashboardController = new DashboardController(simulationService);
        dashboardController.openDashboard();

        SimulationController simulationController = new SimulationController(simulationService);
        SignalController signalController = new SignalController(signalService);
        TrafficController trafficController = new TrafficController(trafficService);

        // Threads
        VehicleGeneratorThread generatorThread = new VehicleGeneratorThread(intersection, vehicleService);
        SignalTimerThread timerThread = new SignalTimerThread(intersection, signalService);
        SimulationClock clock = new SimulationClock(simulationController);
        AnalyticsThread analyticsThread = new AnalyticsThread(intersection);

        generatorThread.start();
        timerThread.start();
        clock.start();
        analyticsThread.start();

        // Console UI
        ConsoleMenu menu = new ConsoleMenu();
        ConsoleDisplay display = new ConsoleDisplay();
        StatisticsView statsView = new StatisticsView();

        boolean exit = false;
        while (!exit) {
            menu.showMenu();
            int choice = menu.getValidChoice(1, 5);

            switch (choice) {
                case 1 -> simulationController.startSimulation();
                case 2 -> simulationController.stopSimulation();
                case 3 -> {
                    display.showIntersectionState(intersection);
                    statsView.printStatistics(simulationService.getStatistics());
                }
                case 4 -> {
                    String dirInput = menu.getDirectionInput();
                    Direction dir = Direction.valueOf(dirInput);
                    simulationService.applyManualOverride(dir, 15);
                }
                case 5 -> {
                    exit = true;
                    simulationController.stopSimulation();
                    generatorThread.stopGenerating();
                    timerThread.stopTimer();
                    clock.stopClock();
                    analyticsThread.stopAnalytics();
                    menu.close();
                    System.out.println("Exiting simulation. Goodbye!");
                }
            }
        }
    }
}
