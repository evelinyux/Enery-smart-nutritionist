package enery.use_case.log_meal;

import enery.entity.meal.MealEntry;

/**
 * Output data for a successfully logged meal.
 */
public final class LogMealOutputData {

    private final String username;
    private final MealEntry mealEntry;

    public LogMealOutputData(
            String username,
            MealEntry mealEntry) {

        this.username = username;
        this.mealEntry = mealEntry;
    }

    public String getUsername() {
        return username;
    }

    public MealEntry getMealEntry() {
        return mealEntry;
    }
}
