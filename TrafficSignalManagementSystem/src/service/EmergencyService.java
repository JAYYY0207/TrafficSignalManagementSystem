package service;

import algorithm.EmergencyPriorityAlgorithm;
import analytics.Logger;
import enums.Direction;
import enums.SignalColor;
import exception.InvalidSignalException;
import model.Intersection;
import model.TrafficStatistics;

public class EmergencyService {

    private SignalService signalService;

    public EmergencyService(SignalService signalService) {
        this.signalService = signalService;
    }

    public boolean checkAndOverride(Intersection intersection, TrafficStatistics stats) throws InvalidSignalException {
        Direction emergencyDirection = EmergencyPriorityAlgorithm.findEmergencyDirection(intersection);

        if (emergencyDirection != null) {
            Logger.alert("Emergency vehicle detected in " + emergencyDirection
                    + " — overriding signal to GREEN.");

            signalService.setSignal(intersection, emergencyDirection, SignalColor.GREEN, 20);
            signalService.setAllOthersToRed(intersection, emergencyDirection, 20);
            stats.incrementEmergencyOverrides();
            return true;
        }
        return false;
    }
}
