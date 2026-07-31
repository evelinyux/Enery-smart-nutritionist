package enery.entity.meal;

import java.time.LocalDate;

/**
 * Creates standard meal entries.
 */
public final class CommonMealEntryFactory implements MealEntryFactory {

    @Override
    public MealEntry create(
            FoodItem foodItem,
            MealType mealType,
            double servings,
            LocalDate date) {

        return new MealEntry(
                foodItem,
                mealType,
                servings,
                date
        );
    }
}
