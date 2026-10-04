package threads;

import model.Intersection;
import model.TrafficSignal;
import service.SignalService;

public class SignalTimerThread extends Thread {

    private Intersection intersection;
    private SignalService signalService;
    private volatile boolean running = true;

    public SignalTimerThread(Intersection intersection, SignalService signalService) {
        this.intersection = intersection;
        this.signalService = signalService;
    }

    @Override
    public void run() {
        while (running) {
            for (TrafficSignal signal : intersection.getAllSignals().values()) {
                signalService.tickSignal(signal);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stopTimer() {
        running = false;
        this.interrupt();
    }
}
