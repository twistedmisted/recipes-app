package ua.kpi.klopotenkoapp.exception;

public class IllegalFileExtensionException extends RuntimeException {

    public IllegalFileExtensionException() {
    }

    public IllegalFileExtensionException(String message) {
        super(message);
    }

    public IllegalFileExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
