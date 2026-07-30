package enery.entity.meal;

import java.time.LocalDateTime;

/**
 * Creates standard meal entries.
 */
public final class CommonMealEntryFactory implements MealEntryFactory {

    @Override
    public MealEntry create(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDateTime dateTime) {

        return new MealEntry(
                foodItem,
                mealType,
                servings,
                dateTime
        );
    }
}
