package enery.data_access.api;

/**
 * Indicates that nutrition information could not be retrieved or parsed.
 */
public final class NutritionApiException extends RuntimeException {

    public NutritionApiException(String message) {
        super(message);
    }

    public NutritionApiException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}
