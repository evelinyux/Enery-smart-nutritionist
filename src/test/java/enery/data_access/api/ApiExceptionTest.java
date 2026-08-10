package enery.data_access.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ApiExceptionTest {

    @Test
    void exerciseExceptionStoresMessage() {
        final ExerciseApiException exception =
                new ExerciseApiException(
                        "exercise failed"
                );

        assertEquals(
                "exercise failed",
                exception.getMessage()
        );
    }

    @Test
    void exerciseExceptionStoresCause() {
        final RuntimeException cause =
                new RuntimeException("cause");

        final ExerciseApiException exception =
                new ExerciseApiException(
                        "exercise failed",
                        cause
                );

        assertEquals(
                "exercise failed",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }

    @Test
    void nutritionExceptionStoresMessage() {
        final NutritionApiException exception =
                new NutritionApiException(
                        "nutrition failed"
                );

        assertEquals(
                "nutrition failed",
                exception.getMessage()
        );
    }

    @Test
    void nutritionExceptionStoresCause() {
        final RuntimeException cause =
                new RuntimeException("cause");

        final NutritionApiException exception =
                new NutritionApiException(
                        "nutrition failed",
                        cause
                );

        assertEquals(
                "nutrition failed",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }
}
