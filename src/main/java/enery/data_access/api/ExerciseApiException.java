package enery.data_access.api;

/**
 * Indicates that an exercise API request could not be completed.
 */
public final class ExerciseApiException extends RuntimeException {

    public ExerciseApiException(String message) {
        super(message);
    }

    public ExerciseApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
