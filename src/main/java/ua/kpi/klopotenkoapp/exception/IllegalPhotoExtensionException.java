package ua.kpi.klopotenkoapp.exception;

public class IllegalPhotoExtensionException extends IllegalFileExtensionException {

    public IllegalPhotoExtensionException() {
    }

    public IllegalPhotoExtensionException(String message) {
        super(message);
    }

    public IllegalPhotoExtensionException(String message, Throwable cause) {
        super(message, cause);
    }
}
