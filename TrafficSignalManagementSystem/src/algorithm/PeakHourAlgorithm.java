package algorithm;

import utils.Constants;
import utils.TimeUtils;

public class PeakHourAlgorithm {

    private PeakHourAlgorithm() {}

    public static boolean isPeakHour() {
        int hour = TimeUtils.currentHour();
        boolean morningPeak = hour >= Constants.PEAK_HOUR_START && hour < Constants.PEAK_HOUR_END;
        boolean eveningPeak = hour >= Constants.EVENING_PEAK_START && hour < Constants.EVENING_PEAK_END;
        return morningPeak || eveningPeak;
    }

    public static int adjustDurationForPeak(int baseDuration) {
        return isPeakHour() ? (int) (baseDuration * 1.5) : baseDuration;
    }
}