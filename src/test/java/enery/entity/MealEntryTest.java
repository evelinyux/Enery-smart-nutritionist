package enery.entity;

import java.time.LocalDateTime;

import enery.entity.meal.FoodItem;
import enery.entity.meal.MealEntry;
import enery.entity.meal.MealType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MealEntryTest {

    @Test
    void calculatesNutritionForMultipleServings() {
        final FoodItem apple = new FoodItem(
                "Apple",
                100.0, // serving size
                95.0,  // calories
                0.5,   // protein
                25.0,  // carbohydrates
                0.3,   // fat
                4.4,   // fiber
                19.0,  // sugar
                2.0    // sodium
        );

        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 8, 30);

        final MealEntry entry = new MealEntry(
                apple,
                MealType.BREAKFAST,
                2.0,
                time
        );

        assertEquals(apple, entry.getFoodItem());
        assertEquals(MealType.BREAKFAST, entry.getMealType());
        assertEquals(2.0, entry.getServings());
        assertEquals(time, entry.getDateTime());

        assertEquals(190.0, entry.getTotalCalories());
        assertEquals(1.0, entry.getTotalProteinInGrams());
        assertEquals(50.0, entry.getTotalCarbohydratesInGrams());
        assertEquals(0.6, entry.getTotalFatInGrams(), 0.0001);
    }
}
