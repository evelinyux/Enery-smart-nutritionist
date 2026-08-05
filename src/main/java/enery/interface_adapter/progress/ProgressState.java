package enery.interface_adapter.progress;

public class ProgressState {
    private double todaysCalories;
    private double dailyCalorieGoal;
    private double dailyProgressPercent;

    private String ProgressError;
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

    public String getProgressError() {
        return ProgressError;
    }

    public void setProgressError(String progressError) {
        ProgressError = progressError;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
