package enery.use_case.daily_check;

import java.time.LocalDate;

import enery.entity.meal.MealType;

/**
 * Data access interface for checking whether meals
 * have been logged for a given date.
 */
public interface DailyCheckMealDataAccessInterface {

    /**
     * Returns whether the user has logged a meal
     * of the given type on the given date.
     */
    boolean hasMeal(
            String username,
            LocalDate date,
            MealType mealType
    );
}