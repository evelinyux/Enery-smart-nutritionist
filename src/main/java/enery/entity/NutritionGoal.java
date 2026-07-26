package enery.entity;

/**
 * Represents a user's daily nutrition targets.
 */
public final class NutritionGoal {

    private final double dailyCalories;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;

    public NutritionGoal(
            double dailyCalories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams) {

        validatePositiveOrZero(dailyCalories, "Daily calories");
        validatePositiveOrZero(proteinInGrams, "Protein");
        validatePositiveOrZero(carbohydratesInGrams, "Carbohydrates");
        validatePositiveOrZero(fatInGrams, "Fat");

        this.dailyCalories = dailyCalories;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
    }

    public double getDailyCalories() {
        return dailyCalories;
    }

    public double getProteinInGrams() {
        return proteinInGrams;
    }

    public double getCarbohydratesInGrams() {
        return carbohydratesInGrams;
    }

    public double getFatInGrams() {
        return fatInGrams;
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
