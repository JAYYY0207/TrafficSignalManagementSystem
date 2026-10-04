package analytics;

import utils.TimeUtils;

public class Logger {

    public static void info(String message) {
        System.out.println("[INFO  " + TimeUtils.formattedTimeNow() + "] " + message);
    }

    public static void warn(String message) {
        System.out.println("[WARN  " + TimeUtils.formattedTimeNow() + "] " + message);
    }

    public static void error(String message) {
        System.out.println("[ERROR " + TimeUtils.formattedTimeNow() + "] " + message);
    }

    public static void alert(String message) {
        System.out.println(">>> ALERT " + TimeUtils.formattedTimeNow() + " >>> " + message);
    }
}