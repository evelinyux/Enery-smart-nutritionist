package enery.data_access.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import enery.entity.FoodItem;
import okhttp3.OkHttpClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApiNinjasNutritionDataAccessObjectTest {

    @Test
    @EnabledIfEnvironmentVariable(
            named = "API_NINJAS_KEY",
            matches = ".+")
    void searchesNutritionApi() {
        final ApiNinjasNutritionDataAccessObject dataAccessObject =
                new ApiNinjasNutritionDataAccessObject(
                        new OkHttpClient(),
                        System.getenv("API_NINJAS_KEY")
                );

        final List<FoodItem> results =
                dataAccessObject.search("1 apple");

        assertFalse(results.isEmpty());

        for (FoodItem foodItem : results) {
            assertFalse(foodItem.getName().isBlank());
            assertTrue(
                    foodItem.getServingSizeInGrams() > 0.0);
            assertTrue(
                    foodItem.getCalories() >= 0.0);
        }
    }
}
