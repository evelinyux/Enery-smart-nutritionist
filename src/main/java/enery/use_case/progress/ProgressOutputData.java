package enery.use_case.progress;

public class ProgressOutputData {
    private final double todaysCalories;
    private final double dailyCalorieGoal;
    private final double dailyProgressPercent;
    private final double proteinInGrams;
    private final double carbohydratesInGrams;
    private final double fatInGrams;

    public ProgressOutputData(
            double todaysCalories,
            double dailyCalorieGoal,
            double dailyProgressPercent,
            double proteinInGrams,
            double carbohydratesInGrams,
            double fatInGrams) {
        this.todaysCalories = todaysCalories;
        this.dailyCalorieGoal = dailyCalorieGoal;
        this.dailyProgressPercent = dailyProgressPercent;
        this.proteinInGrams = proteinInGrams;
        this.carbohydratesInGrams = carbohydratesInGrams;
        this.fatInGrams = fatInGrams;
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
}
