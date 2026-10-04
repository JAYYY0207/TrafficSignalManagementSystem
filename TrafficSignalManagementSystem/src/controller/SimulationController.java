package controller;

import exception.InvalidSignalException;
import service.SimulationService;

public class SimulationController {

    private SimulationService simulationService;

    public SimulationController(SimulationService simulationService) {
        this.simulationService = simulationService;
    }

    public void startSimulation() {
        simulationService.start();
    }

    public void stopSimulation() {
        simulationService.stop();
    }

    public void runTick() throws InvalidSignalException {
        simulationService.tick();
    }

    public boolean isRunning() {
        return simulationService.isRunning();
    }
}