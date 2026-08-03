package enery.interface_adapter.tracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import enery.use_case.tracking.DailyTrackingSummary;
import enery.use_case.tracking.TrackingPeriod;

/**
 * Stores the information displayed by the tracking screen.
 */
public final class TrackingState {

    private TrackingPeriod period = TrackingPeriod.DAILY;
    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate = LocalDate.now();

    private double caloriesConsumed;
    private double caloriesBurned;
    private double netCalories;

    private double proteinInGrams;
    private double carbohydratesInGrams;
    private double fatInGrams;
    private double fiberInGrams;
    private double sugarInGrams;
    private double sodiumInMilligrams;

    private double averageCaloriesConsumed;
    private double averageCaloriesBurned;
    private double averageNetCalories;

    private List<DailyTrackingSummary> dailySummaries =
            new ArrayList<>();

    private String errorMessage = "";

    public TrackingPeriod getPeriod() {
        return period;
    }

    public void setPeriod(TrackingPeriod period) {
        this.period = period;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public double getFiberInGrams() { return fiberInGrams; }

    public void setFiberInGrams(double fiberInGrams) { this.fiberInGrams = fiberInGrams; }

    public double getSugarInGrams() { return sugarInGrams; }

    public void setSugarInGrams(double sugarInGrams) { this.sugarInGrams = sugarInGrams; }

    public double getSodiumInMilligrams() { return sodiumInMilligrams; }

    public void setSodiumInMilligrams(double sodiumInMilligrams) { this.sodiumInMilligrams = sodiumInMilligrams; }

    public double getAverageCaloriesConsumed() {
        return averageCaloriesConsumed;
    }

    public void setAverageCaloriesConsumed(
            double averageCaloriesConsumed) {

        this.averageCaloriesConsumed =
                averageCaloriesConsumed;
    }

    public double getAverageCaloriesBurned() {
        return averageCaloriesBurned;
    }

    public void setAverageCaloriesBurned(
            double averageCaloriesBurned) {

        this.averageCaloriesBurned =
                averageCaloriesBurned;
    }

    public double getAverageNetCalories() {
        return averageNetCalories;
    }

    public void setAverageNetCalories(
            double averageNetCalories) {

        this.averageNetCalories =
                averageNetCalories;
    }

    public List<DailyTrackingSummary> getDailySummaries() {
        return Collections.unmodifiableList(
                dailySummaries);
    }

    public void setDailySummaries(
            List<DailyTrackingSummary> dailySummaries) {

        this.dailySummaries =
                new ArrayList<>(dailySummaries);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
