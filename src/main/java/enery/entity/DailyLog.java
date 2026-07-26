package enery.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores all meal and exercise entries for one calendar date.
 */
public final class DailyLog {

    private final LocalDate date;
    private final List<MealEntry> mealEntries = new ArrayList<>();
    private final List<ExerciseEntry> exerciseEntries = new ArrayList<>();

    public DailyLog(LocalDate date) {
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<MealEntry> getMealEntries() {
        return Collections.unmodifiableList(mealEntries);
    }

    public List<ExerciseEntry> getExerciseEntries() {
        return Collections.unmodifiableList(exerciseEntries);
    }

    public void addMealEntry(MealEntry mealEntry) {
        mealEntries.add(mealEntry);
    }

    public void addExerciseEntry(ExerciseEntry exerciseEntry) {
        exerciseEntries.add(exerciseEntry);
    }

    public double getTotalCaloriesConsumed() {
        double total = 0.0;

        for (MealEntry mealEntry : mealEntries) {
            total += mealEntry.getTotalCalories();
        }

        return total;
    }

    public double getTotalProteinInGrams() {
        double total = 0.0;

        for (MealEntry mealEntry : mealEntries) {
            total += mealEntry.getTotalProteinInGrams();
        }

        return total;
    }

    public double getTotalCarbohydratesInGrams() {
        double total = 0.0;

        for (MealEntry mealEntry : mealEntries) {
            total += mealEntry.getTotalCarbohydratesInGrams();
        }

        return total;
    }

    public double getTotalFatInGrams() {
        double total = 0.0;

        for (MealEntry mealEntry : mealEntries) {
            total += mealEntry.getTotalFatInGrams();
        }

        return total;
    }

    public double getTotalCaloriesBurned() {
        double total = 0.0;

        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            total += exerciseEntry.getCaloriesBurned();
        }

        return total;
    }

    public double getNetCalories() {
        return getTotalCaloriesConsumed() - getTotalCaloriesBurned();
    }
}
