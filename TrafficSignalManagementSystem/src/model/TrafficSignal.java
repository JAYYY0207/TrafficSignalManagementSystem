package model;

import enums.Direction;
import enums.SignalColor;

public class TrafficSignal {

    private String signalId;
    private Direction controlsDirection;
    private SignalState currentState;

    public TrafficSignal(String signalId, Direction controlsDirection) {
        this.signalId = signalId;
        this.controlsDirection = controlsDirection;
        this.currentState = new SignalState(SignalColor.RED, 0);
    }

    public String getSignalId() {
        return signalId;
    }

    public Direction getControlsDirection() {
        return controlsDirection;
    }

    public SignalState getCurrentState() {
        return currentState;
    }

    public void changeColor(SignalColor color, int durationSeconds) {
        currentState.setColor(color);
        currentState.setRemainingSeconds(durationSeconds);
    }

    public boolean isGreen() {
        return currentState.getColor() == SignalColor.GREEN;
    }

    @Override
    public String toString() {
        return "Signal[" + signalId + " | " + controlsDirection + " | " + currentState + "]";
    }
}