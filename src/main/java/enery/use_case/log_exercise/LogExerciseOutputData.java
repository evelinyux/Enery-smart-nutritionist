package enery.use_case.log_exercise;

import enery.entity.exercise.ExerciseEntry;

/**
 * Output data for a successfully logged exercise.
 */
public final class LogExerciseOutputData {

    private final String username;
    private final ExerciseEntry exerciseEntry;

    public LogExerciseOutputData(
            String username,
            ExerciseEntry exerciseEntry) {

        this.username = username;
        this.exerciseEntry = exerciseEntry;
    }

    public String getUsername() {
        return username;
    }

    public ExerciseEntry getExerciseEntry() {
        return exerciseEntry;
    }
}
