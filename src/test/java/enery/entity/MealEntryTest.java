package enery.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MealEntryTest {

    @Test
    void calculatesNutritionForMultipleServings() {
        final FoodItem apple = new FoodItem(
                "Apple",
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
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
