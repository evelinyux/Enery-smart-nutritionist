package enery.use_case.tracking;

import java.time.LocalDate;

/**
 * Contains the calculated totals for one day.
 */
public final class TrackingOutputData {

    private final LocalDate date;
    private final double caloriesConsumed;
    private final double caloriesBurned;
    private final double netCalories;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;

    public TrackingOutputData(
            LocalDate date,
            double caloriesConsumed,
            double caloriesBurned,
            double netCalories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams) {

        this.date = date;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
        this.netCalories = netCalories;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public double getNetCalories() {
        return netCalories;
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
}
