package enery.use_case.progress;

public class ProgressOutputData {
    private final double todaysCalories;
    private final double dailyCalorieGoal;
    private final double dailyProgressPercent;

    public ProgressOutputData(
            double todaysCalories,
            double dailyCalorieGoal,
            double dailyProgressPercent) {
        this.todaysCalories = todaysCalories;
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.dailyProgressPercent = dailyProgressPercent;
    }

    public double getDailyCalorieGoal() {
        return dailyCalorieGoal;
    }

    public double getTodaysCalories() {
        return todaysCalories;
    }

    public double getDailyProgressPercent() {
        return dailyProgressPercent;
    }
}
