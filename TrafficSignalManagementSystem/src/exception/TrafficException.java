package exception;

public class TrafficException extends Exception {

    public TrafficException(String message) {
        super(message);
    }

    public TrafficException(String message, Throwable cause) {
        super(message, cause);
    }
}