package model;

import java.util.HashMap;
import java.util.Map;
import enums.Direction;

public class Intersection {

    private String intersectionId;
    private Map<Direction, Lane> lanes;
    private Map<Direction, TrafficSignal> signals;

    public Intersection(String intersectionId) {
        this.intersectionId = intersectionId;
        this.lanes = new HashMap<>();
        this.signals = new HashMap<>();
    }

    public String getIntersectionId() {
        return intersectionId;
    }

    public void addLane(Lane lane) {
        lanes.put(lane.getDirection(), lane);
    }

    public void addSignal(TrafficSignal signal) {
        signals.put(signal.getControlsDirection(), signal);
    }

    public Lane getLane(Direction direction) {
        return lanes.get(direction);
    }

    public TrafficSignal getSignal(Direction direction) {
        return signals.get(direction);
    }

    public Map<Direction, Lane> getAllLanes() {
        return lanes;
    }

    public Map<Direction, TrafficSignal> getAllSignals() {
        return signals;
    }

    @Override
    public String toString() {
        return "Intersection[" + intersectionId + " | lanes=" + lanes.size() + ", signals=" + signals.size() + "]";
    }
}
