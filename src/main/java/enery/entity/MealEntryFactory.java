package enery.entity;

import java.time.LocalDateTime;

/**
 * Creates meal entries.
 */
public interface MealEntryFactory {

    MealEntry create(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDateTime dateTime
    );
}
