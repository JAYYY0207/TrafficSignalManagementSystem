package utils;

import enums.Direction;
import enums.VehicleCategory;
import enums.SignalColor;
import exception.InvalidSignalException;
import exception.VehicleNotFoundException;

public class ValidationUtils {

    private ValidationUtils() {
        // utility class — prevent instantiation
    }

    // ---------- Numeric validation ----------

    public static boolean isPositive(int value) {
        return value > 0;
    }

    public static boolean isPositive(double value) {
        return value > 0;
    }

    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }

    // ---------- Signal timing validation ----------

    public static void validateSignalDuration(int durationSeconds) throws InvalidSignalException {
        if (!isPositive(durationSeconds)) {
            throw new InvalidSignalException(
                    "Signal duration must be positive. Received: " + durationSeconds);
        }
        if (durationSeconds > 300) {
            throw new InvalidSignalException(
                    "Signal duration unrealistically high (> 300s): " + durationSeconds);
        }
    }

    // ---------- Enum / string validation ----------

    public static boolean isValidDirection(String input) {
        if (input == null || input.isBlank()) return false;
        try {
            Direction.valueOf(input.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidVehicleCategory(String input) {
        if (input == null || input.isBlank()) return false;
        try {
            VehicleCategory.valueOf(input.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidSignalColor(String input) {
        if (input == null || input.isBlank()) return false;
        try {
            SignalColor.valueOf(input.trim().toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    // ---------- Console menu input validation ----------

    public static boolean isValidMenuChoice(String input, int min, int max) {
        if (input == null || input.isBlank()) return false;
        try {
            int choice = Integer.parseInt(input.trim());
            return isInRange(choice, min, max);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ---------- Vehicle / ID validation ----------

    public static void validateVehicleId(String vehicleId, boolean exists) throws VehicleNotFoundException {
        if (vehicleId == null || vehicleId.isBlank()) {
            throw new VehicleNotFoundException("Vehicle ID cannot be null or empty.");
        }
        if (!exists) {
            throw new VehicleNotFoundException("No vehicle found with ID: " + vehicleId);
        }
    }

    // ---------- Config validation ----------

    public static boolean isValidConfigInt(String value) {
        if (value == null || value.isBlank()) return false;
        try {
            Integer.parseInt(value.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
