package enery.interface_adapter.progress;

public class ProgressState {
    private double todaysCalories;
    private double dailyCalorieGoal;
    private double dailyProgressPercent;
    private double todaysProtein;
    private double todaysCarbohydrates;
    private double todaysFats;

    private String progressError;
    private String successMessage;

    public double getTodaysCalories() {
        return todaysCalories;
    }

    public void setTodaysCalories(double todaysCalories) {
        this.todaysCalories = todaysCalories;
    }

    public double getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public void setDailyCalorieGoal(double dailyCalorieGoal) {
        this.dailyCalorieGoal = dailyCalorieGoal;
    }

    public double getDailyProgressPercent() {
        return dailyProgressPercent;
    }

    public void setDailyProgressPercent(double dailyProgressPercent) {
        this.dailyProgressPercent = dailyProgressPercent;
    }

    public double getTodaysProtein() {
        return todaysProtein;
    }

    public void setTodaysProtein(double protein) {
        this.todaysProtein = protein;
    }

    public double getTodaysCarboydrates() {
        return todaysCarbohydrates;
    }

    public void setTodaysCarbohydrates(double carboydrates) {
        this.todaysCarbohydrates = carboydrates;
    }

    public double getTodaysFats() {
        return todaysFats;
    }

    public void setTodaysFats(double fats) {
        this.todaysFats = fats;
    }

    public String getProgressError() {
        return progressError;
    }

    public void setProgressError(String progressError) {
        this.progressError = progressError;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
