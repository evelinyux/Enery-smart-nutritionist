package enery.entity;

import java.time.LocalDateTime;

/**
 * Represents one exercise activity recorded by a user.
 */
public final class ExerciseEntry {

    private final String exerciseName;
    private final int durationInMinutes;
    private final double caloriesBurned;
    private final LocalDateTime dateTime;

    public ExerciseEntry(
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDateTime dateTime) {

        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
        this.caloriesBurned = caloriesBurned;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
