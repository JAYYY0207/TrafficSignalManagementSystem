package threads;

import controller.SimulationController;
import analytics.Logger;
import utils.Constants;

public class SimulationClock extends Thread {

    private SimulationController simulationController;
    private volatile boolean running = true;

    public SimulationClock(SimulationController simulationController) {
        this.simulationController = simulationController;
    }

    @Override
    public void run() {
        while (running) {
            try {
                if (simulationController.isRunning()) {
                    simulationController.runTick();
                }
                Thread.sleep(Constants.SIMULATION_TICK_MS);
            } catch (Exception e) {
                Logger.error("Simulation tick failed: " + e.getMessage());
            }
        }
    }

    public void stopClock() {
        running = false;
        this.interrupt();
    }
}