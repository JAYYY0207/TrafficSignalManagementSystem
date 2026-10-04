package console;

import enums.Direction;
import model.Intersection;
import model.Lane;
import model.TrafficSignal;

public class ConsoleDisplay {

    public void showIntersectionState(Intersection intersection) {
        System.out.println("\n----- Intersection: " + intersection.getIntersectionId() + " -----");
        for (Direction dir : Direction.values()) {
            Lane lane = intersection.getLane(dir);
            TrafficSignal signal = intersection.getSignal(dir);
            if (lane != null && signal != null) {
                System.out.println(dir + " | " + signal.getCurrentState()
                        + " | Waiting: " + lane.getQueueLength());
            }
        }
        System.out.println("--------------------------------------");
    }
}