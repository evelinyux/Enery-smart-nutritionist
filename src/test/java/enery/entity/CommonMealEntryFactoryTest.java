package enery.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonMealEntryFactoryTest {

    @Test
    void createsMealEntry() {
        final FoodItem food = new FoodItem(
                "Apple",
                100.0,
                95.0,
                0.5,
                25.0,
                0.3,
                4.4,
                19.0,
                2.0
        );

        final LocalDateTime time =
                LocalDateTime.of(2026, 7, 26, 8, 0);

        final MealEntryFactory factory =
                new CommonMealEntryFactory();

        final MealEntry entry = factory.create(
                food,
                MealType.BREAKFAST,
                2.0,
                time
        );

        assertEquals(food, entry.getFoodItem());
        assertEquals(MealType.BREAKFAST, entry.getMealType());
        assertEquals(2.0, entry.getServings());
        assertEquals(time, entry.getDateTime());
    }
}
