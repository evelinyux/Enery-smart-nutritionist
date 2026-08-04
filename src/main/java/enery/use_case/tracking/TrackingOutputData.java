package enery.use_case.tracking;

import java.time.LocalDate;
import java.util.List;

/**
 * Contains the totals and daily breakdown for a tracking period.
 */
public final class TrackingOutputData {

    private final TrackingPeriod period;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private final double caloriesConsumed;
    private final double caloriesBurned;
    private final double netCalories;

    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final double fiberInGrams;
    private final double sugarInGrams;
    private final double sodiumInMilligrams;

    private final double averageCaloriesConsumed;
    private final double averageCaloriesBurned;
    private final double averageNetCalories;

    private final List<DailyTrackingSummary> dailySummaries;

    public TrackingOutputData(
            TrackingPeriod period,
            LocalDate startDate,
            LocalDate endDate,
            double caloriesConsumed,
            double caloriesBurned,
            double netCalories,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            double fiberInGrams,
            double sugarInGrams,
            double sodiumInMilligrams,
            double averageCaloriesConsumed,
            double averageCaloriesBurned,
            double averageNetCalories,
            List<DailyTrackingSummary> dailySummaries) {

        this.period = period;
        this.startDate = startDate;
        this.endDate = endDate;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
        this.netCalories = netCalories;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
        this.fiberInGrams = fiberInGrams;
        this.sugarInGrams = sugarInGrams;
        this.sodiumInMilligrams = sodiumInMilligrams;
        this.averageCaloriesConsumed = averageCaloriesConsumed;
        this.averageCaloriesBurned = averageCaloriesBurned;
        this.averageNetCalories = averageNetCalories;
        this.dailySummaries = List.copyOf(dailySummaries);
    }

    public TrackingPeriod getPeriod() {
        return period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
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

    public double getFiberInGrams() { return fiberInGrams; }

    public double getSugarInGrams() { return sugarInGrams; }

    public double getSodiumInMilligrams() { return sodiumInMilligrams; }

    public double getAverageCaloriesConsumed() {
        return averageCaloriesConsumed;
    }

    public double getAverageCaloriesBurned() {
        return averageCaloriesBurned;
    }

    public double getAverageNetCalories() {
        return averageNetCalories;
    }

    public List<DailyTrackingSummary> getDailySummaries() {
        return dailySummaries;
    }
}
