package enery.entity.meal;

import java.time.LocalDate;

/**
 * Creates meal entries.
 */
public interface MealEntryFactory {

    MealEntry create(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDate date
    );
}
