package enery.data_access.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import enery.entity.exercise.ExerciseSearchResult;
import okhttp3.OkHttpClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiNinjasCaloriesBurnedDataAccessObjectTest {

    @Test
    @EnabledIfEnvironmentVariable(
            named = "API_NINJAS_KEY",
            matches = ".+")
    void searchesExerciseApi() {
        final ApiNinjasCaloriesBurnedDataAccessObject dao =
                new ApiNinjasCaloriesBurnedDataAccessObject(
                        new OkHttpClient(),
                        System.getenv("API_NINJAS_KEY")
                );

        final List<ExerciseSearchResult> results =
                dao.search(
                        "cycling",
                        160.0,
                        30
                );

        assertFalse(results.isEmpty());

        for (ExerciseSearchResult result : results) {
            assertTrue(result.getDurationInMinutes() > 0);
            assertTrue(result.getTotalCalories() >= 0.0);
        }
    }
}
