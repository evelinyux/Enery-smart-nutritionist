package enery.use_case.tracking;

import java.time.LocalDate;

/**
 * Stores calorie totals for one day within a tracking period.
 */
public final class DailyTrackingSummary {

    private final LocalDate date;
    private final double caloriesConsumed;
    private final double caloriesBurned;
    private final double netCalories;

    public DailyTrackingSummary(
            LocalDate date,
            double caloriesConsumed,
            double caloriesBurned,
            double netCalories) {

        this.date = date;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
        this.netCalories = netCalories;
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
}
