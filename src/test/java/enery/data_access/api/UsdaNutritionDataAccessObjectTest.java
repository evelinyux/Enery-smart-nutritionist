package enery.data_access.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import enery.entity.meal.FoodItem;
import okhttp3.OkHttpClient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UsdaNutritionDataAccessObjectTest {

    @Test
    @EnabledIfEnvironmentVariable(
            named = "USDA_API_KEY",
            matches = ".+")
    void searchesUsdaFoodDataCentral() {
        final UsdaNutritionDataAccessObject dataAccessObject =
                new UsdaNutritionDataAccessObject(
                        new OkHttpClient(),
                        System.getenv("USDA_API_KEY")
                );

        final List<FoodItem> results =
                dataAccessObject.search("rice");

        assertFalse(results.isEmpty());

        for (FoodItem food : results) {
            assertFalse(food.getName().isBlank());
            assertTrue(
                    food.getServingSizeInGrams() > 0.0);
            assertTrue(food.getCalories() >= 0.0);
            assertTrue(
                    food.getProteinInGrams() >= 0.0);
        }
    }
}
