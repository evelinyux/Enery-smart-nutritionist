package enery.interface_adapter.tracking;

import java.time.LocalDate;

/**
 * Stores the data currently displayed by the tracking screen.
 */
public final class TrackingState {

    private LocalDate date = LocalDate.now();
    private double caloriesConsumed;
    private double caloriesBurned;
    private double netCalories;
    private double proteinInGrams;
    private double carbohydratesInGrams;
    private double fatInGrams;
    private String errorMessage = "";

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(double caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getNetCalories() {
        return netCalories;
    }

    public void setNetCalories(double netCalories) {
        this.netCalories = netCalories;
    }

    public double getProteinInGrams() {
        return proteinInGrams;
    }

    public void setProteinInGrams(double proteinInGrams) {
        this.proteinInGrams = proteinInGrams;
    }

    public double getCarbohydratesInGrams() {
        return carbohydratesInGrams;
    }

    public void setCarbohydratesInGrams(
            double carbohydratesInGrams) {

        this.carbohydratesInGrams =
                carbohydratesInGrams;
    }

    public double getFatInGrams() {
        return fatInGrams;
    }

    public void setFatInGrams(double fatInGrams) {
        this.fatInGrams = fatInGrams;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
