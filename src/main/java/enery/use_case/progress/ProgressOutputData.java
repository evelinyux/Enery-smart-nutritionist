package enery.use_case.progress;

public class ProgressOutputData {
    private final double todaysCalories;
    private final double dailyCalorieGoal;
    private final double dailyProgressPercent;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;
    private final double totalCalorieGoal;
    private final double totalCalories;
    private final double totalProgressPercent;
    private final int daysSoFar;

    public ProgressOutputData(
            double todaysCalories,
            double dailyCalorieGoal,
            double dailyProgressPercent,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams,
            double totalCalorieGoal,
            double totalCalories,
            double totalProgressPercent,
            int daysSoFar) {
        this.todaysCalories = todaysCalories;
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.dailyProgressPercent = dailyProgressPercent;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
        this.totalCalorieGoal = totalCalorieGoal;
        this.totalCalories = totalCalories;
        this.totalProgressPercent = totalProgressPercent;
        this.daysSoFar = daysSoFar;
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

    public double getProteinInGrams() {
        return proteinInGrams;
    }

    public double getCarbohydratesInGrams() {
        return carbohydratesInGrams;
    }

    public double getFatInGrams() {
        return fatInGrams;
    }

    public double getTotalCalorieGoal() {
        return totalCalorieGoal;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public double getTotalProgressPercent() {
        return totalProgressPercent;
    }

    public int getDaysSoFar() {
        return daysSoFar;
    }
}
