package utils;

public class Constants {

    private Constants() {}

    public static final int DEFAULT_GREEN_DURATION = 15;
    public static final int DEFAULT_YELLOW_DURATION = 3;
    public static final int DEFAULT_RED_DURATION = 18;

    public static final int VEHICLE_SPAWN_INTERVAL_MS = 900;
    public static final int SIMULATION_TICK_MS = 1500;
    public static final int VEHICLES_PROCESSED_PER_TICK = 1;

    public static final int PEAK_HOUR_START = 8;   // 8 AM
    public static final int PEAK_HOUR_END = 10;    // 10 AM
    public static final int EVENING_PEAK_START = 17; // 5 PM
    public static final int EVENING_PEAK_END = 20;   // 8 PM

    public static final int HIGH_DENSITY_THRESHOLD = 8;
    public static final int MEDIUM_DENSITY_THRESHOLD = 4;

    public static final String CONFIG_FILE_PATH = "resources/config.properties";
}