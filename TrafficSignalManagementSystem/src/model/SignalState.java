package model;

import enums.SignalColor;

public class SignalState {

    private SignalColor color;
    private int remainingSeconds;

    public SignalState(SignalColor color, int remainingSeconds) {
        this.color = color;
        this.remainingSeconds = remainingSeconds;
    }

    public SignalColor getColor() {
        return color;
    }

    public void setColor(SignalColor color) {
        this.color = color;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }

    public void tickDown() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
        }
    }

    @Override
    public String toString() {
        return "[" + color + " - " + remainingSeconds + "s]";
    }
}