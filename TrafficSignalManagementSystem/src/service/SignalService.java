package service;

import enums.Direction;
import enums.SignalColor;
import exception.InvalidSignalException;
import model.Intersection;
import model.TrafficSignal;
import utils.ValidationUtils;

public class SignalService {

    public void setSignal(Intersection intersection, Direction direction, SignalColor color, int duration)
            throws InvalidSignalException {

        ValidationUtils.validateSignalDuration(duration);

        TrafficSignal signal = intersection.getSignal(direction);
        if (signal == null) {
            throw new InvalidSignalException("No signal found for direction: " + direction);
        }
        signal.changeColor(color, duration);
    }

    public void setAllOthersToRed(Intersection intersection, Direction excludeDirection, int duration)
            throws InvalidSignalException {

        for (Direction dir : intersection.getAllSignals().keySet()) {
            if (dir != excludeDirection) {
                setSignal(intersection, dir, SignalColor.RED, duration);
            }
        }
    }

    public void tickSignal(TrafficSignal signal) {
        signal.getCurrentState().tickDown();
    }
}