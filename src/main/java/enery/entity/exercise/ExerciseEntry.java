package enery.entity.exercise;

import java.time.LocalDate;

/**
 * Represents one exercise activity recorded by a user.
 */
public final class ExerciseEntry {

    private final String exerciseName;
    private final int durationInMinutes;
    private final double caloriesBurned;
    private final LocalDate date;

    public ExerciseEntry(
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDate date) {

        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public LocalDate getDate() {
        return date;
    }
}
