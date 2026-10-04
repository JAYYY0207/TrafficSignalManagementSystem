package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileUtils {

    private FileUtils() {}

    public static Properties loadConfig(String path) {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Warning: could not load config file at " + path + ". Using defaults.");
        }
        return properties;
    }

    public static int getIntProperty(Properties properties, String key, int defaultValue) {
        String value = properties.getProperty(key);
        if (value == null || !ValidationUtils.isValidConfigInt(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value.trim());
    }
}