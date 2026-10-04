package service;

import algorithm.PeakHourAlgorithm;
import analytics.Logger;

public class PeakHourService {

    public int getAdjustedDuration(int baseDuration) {
        if (PeakHourAlgorithm.isPeakHour()) {
            Logger.info("Peak hour detected — extending signal durations.");
            return PeakHourAlgorithm.adjustDurationForPeak(baseDuration);
        }
        return baseDuration;
    }

    public boolean isCurrentlyPeakHour() {
        return PeakHourAlgorithm.isPeakHour();
    }
}
