package controller;

import enums.Direction;
import enums.SignalColor;
import exception.InvalidSignalException;
import model.Intersection;
import service.SignalService;

public class SignalController {

    private SignalService signalService;

    public SignalController(SignalService signalService) {
        this.signalService = signalService;
    }

    public void manualOverride(Intersection intersection, Direction direction, SignalColor color, int duration)
            throws InvalidSignalException {
        signalService.setSignal(intersection, direction, color, duration);
    }
}
