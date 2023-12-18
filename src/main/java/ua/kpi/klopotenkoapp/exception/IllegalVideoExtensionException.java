package ua.kpi.klopotenkoapp.exception;

public class IllegalVideoExtensionException extends IllegalFileExtensionException {

    public IllegalVideoExtensionException() {
    }

    public IllegalVideoExtensionException(String message) {
        super(message);
    }

    public IllegalVideoExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
