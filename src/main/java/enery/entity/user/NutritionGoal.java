package enery.entity.user;

/**
 * Value Object representing a user's daily nutrition targets (calories and macronutrients).
 */
public final class NutritionGoal {

    // Health Canada / Standard DRI Defaults
    public static final double DEFAULT_DAILY_CARBS_G = 325.0;
    public static final double DEFAULT_DAILY_FAT_G = 78.0;
    public static final double DEFAULT_DAILY_PROTEIN_G = 175.0;

    private final double dailyCalories;
    private final double dailyCarbsGrams;
    private final double dailyFatGrams;
    private final double dailyProteinGrams;

    /**
     * Constructs a custom NutritionGoal.
     */
    public NutritionGoal(
            double dailyCalories,
            double dailyCarbsGrams,
            double dailyFatGrams,
            double dailyProteinGrams) {

        validatePositiveOrZero(dailyCalories, "Daily calories");
        validatePositiveOrZero(dailyCarbsGrams, "Daily carbs");
        validatePositiveOrZero(dailyFatGrams, "Daily fat");
        validatePositiveOrZero(dailyProteinGrams, "Daily protein");

        this.dailyCalories = dailyCalories;
        this.dailyCarbsGrams = dailyCarbsGrams;
        this.dailyFatGrams = dailyFatGrams;
        this.dailyProteinGrams = dailyProteinGrams;
    }

    /**
     * Convenience constructor using Health Canada default macros for a calculated calorie target.
     */
    public NutritionGoal(double dailyCalories) {
        this(dailyCalories, DEFAULT_DAILY_CARBS_G, DEFAULT_DAILY_FAT_G, DEFAULT_DAILY_PROTEIN_G);
    }

    public double getDailyCalories() {
        return dailyCalories;
    }

    public double getDailyCarbsGrams() {
        return dailyCarbsGrams;
    }

    public double getDailyFatGrams() {
        return dailyFatGrams;
    }

    public double getDailyProteinGrams() {
        return dailyProteinGrams;
    }

    private static void validatePositiveOrZero(double value, String fieldName) {
        if (value < 0.0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }
}