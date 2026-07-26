package enery.use_case.log_exercise;

import java.time.LocalDateTime;

/**
 * Input data for logging an exercise activity.
 */
public final class LogExerciseInputData {

    private final String username;
    private final String exerciseName;
    private final int durationInMinutes;
    private final double caloriesBurned;
    private final LocalDateTime dateTime;

    public LogExerciseInputData(
            String username,
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDateTime dateTime) {

        this.username = username;
        this.exerciseName = exerciseName;
        this.durationInMinutes = durationInMinutes;
        this.caloriesBurned = caloriesBurned;
        this.dateTime = dateTime;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
