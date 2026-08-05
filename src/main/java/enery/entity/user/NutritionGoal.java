package enery.entity.user;

/**
 * Represents a user's daily nutrition targets.
 */
public final class NutritionGoal {

    private final double dailyCalories;

    public NutritionGoal(
            double dailyCalories) {

        validatePositiveOrZero(dailyCalories, "Daily calories");


        this.dailyCalories = dailyCalories;
    }

    public double getDailyCalories() {
        return dailyCalories;
    }


    private static void validatePositiveOrZero(
            double value,
            String fieldName) {

        if (value < 0.0) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be negative.");
        }
    }
}
