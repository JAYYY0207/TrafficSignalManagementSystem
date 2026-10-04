package exception;

public class InvalidSignalException extends Exception {

    public InvalidSignalException(String message) {
        super(message);
    }

    public InvalidSignalException(String message, Throwable cause) {
        super(message, cause);
    }
}
