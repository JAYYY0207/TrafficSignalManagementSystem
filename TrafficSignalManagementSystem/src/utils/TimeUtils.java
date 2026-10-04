package utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private TimeUtils() {}

    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static int currentHour() {
        return LocalTime.now().getHour();
    }

    public static String formattedTimeNow() {
        return LocalTime.now().format(TIME_FORMAT);
    }

    public static String formatSecondsAsDuration(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public static double elapsedSeconds(long startTimeMillis) {
        return (System.currentTimeMillis() - startTimeMillis) / 1000.0;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}