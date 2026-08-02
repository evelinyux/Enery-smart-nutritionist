package enery.use_case.log_exercise;

import java.time.LocalDate;

/**
 * Input data for logging an exercise activity.
 */
public final class LogExerciseInputData {

    private final String username;
    private final String exerciseName;
    private final int durationInMinutes;
    private final double caloriesBurned;
    private final LocalDate date;

    public LogExerciseInputData(
            String username,
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDate date) {

        this.username = username;
        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
        this.caloriesBurned = caloriesBurned;
        this.date = date;
    }

    public String getUsername() {
        return username;
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
