package service;

import algorithm.DensityCalculator;
import algorithm.EmergencyPriorityAlgorithm;
import algorithm.SignalScheduler;
import analytics.Logger;
import enums.Direction;
import enums.SignalColor;
import exception.InvalidSignalException;
import model.Intersection;
import model.Lane;
import model.TrafficStatistics;
import model.Vehicle;

public class SimulationService {

    private final Intersection intersection;
    private final VehicleService vehicleService;
    private final TrafficService trafficService;
    private final SignalService signalService;
    private final EmergencyService emergencyService;
    private final PeakHourService peakHourService;
    private final TrafficStatistics statistics;

    private boolean running;

    // Manual override state
    private Direction manualOverrideDirection = null;
    private int manualOverrideTicksRemaining = 0;

    private static final int VEHICLES_PER_GREEN_TICK = 2;

    public SimulationService(Intersection intersection, VehicleService vehicleService,
                             TrafficService trafficService, SignalService signalService,
                             EmergencyService emergencyService, PeakHourService peakHourService) {
        this.intersection = intersection;
        this.vehicleService = vehicleService;
        this.trafficService = trafficService;
        this.signalService = signalService;
        this.emergencyService = emergencyService;
        this.peakHourService = peakHourService;
        this.statistics = new TrafficStatistics();
        this.running = false;
    }

    public void start() {
        running = true;
        Logger.info("Simulation started.");
    }

    public void stop() {
        running = false;
        Logger.info("Simulation stopped.");
    }

    public boolean isRunning() {
        return running;
    }

    public TrafficStatistics getStatistics() {
        return statistics;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * Manually forces a direction to GREEN (all others RED) and holds that
     * state for the given number of ticks, during which normal scheduling
     * and emergency overrides are paused. Vehicles still get processed
     * during the hold so the lane visibly clears instead of freezing.
     */
    public void applyManualOverride(Direction direction, int holdSeconds) throws InvalidSignalException {
        this.manualOverrideDirection = direction;
        this.manualOverrideTicksRemaining = holdSeconds;

        signalService.setSignal(intersection, direction, SignalColor.GREEN, holdSeconds);
        signalService.setAllOthersToRed(intersection, direction, holdSeconds);

        Logger.info("Manual override applied: " + direction + " GREEN for " + holdSeconds + "s");
    }

    public void tick() throws InvalidSignalException {
        if (!running) return;

        if (manualOverrideTicksRemaining > 0) {
            manualOverrideTicksRemaining--;
            processVehicles(manualOverrideDirection);

            if (manualOverrideTicksRemaining == 0) {
                Logger.info("Manual override ended for " + manualOverrideDirection);
                manualOverrideDirection = null;
            }

            updateQueueStats();
            return;
        }

        Direction emergencyDirection = EmergencyPriorityAlgorithm.findEmergencyDirection(intersection);

        if (emergencyDirection != null) {
            emergencyService.checkAndOverride(intersection, statistics);
            processVehicles(emergencyDirection);
        } else {
            Direction next = SignalScheduler.selectNextGreenDirection(intersection);
            if (next != null) {
                int density = DensityCalculator.calculateDensity(intersection.getLane(next));
                int duration = SignalScheduler.calculateGreenDuration(density);
                duration = peakHourService.getAdjustedDuration(duration);

                signalService.setSignal(intersection, next, SignalColor.GREEN, duration);
                signalService.setAllOthersToRed(intersection, next, duration);

                processVehicles(next);
            }
        }

        updateQueueStats();
    }

    private void processVehicles(Direction direction) {
        if (direction == null) return;
        for (int i = 0; i < VEHICLES_PER_GREEN_TICK; i++) {
            Vehicle processed = trafficService.processGreenDirection(intersection, direction);
            if (processed != null) {
                statistics.incrementVehiclesProcessed();
            } else {
                break;
            }
        }
    }

    private void updateQueueStats() {
        for (Lane lane : intersection.getAllLanes().values()) {
            statistics.updateMaxQueueLength(lane.getQueueLength());
        }
    }
}