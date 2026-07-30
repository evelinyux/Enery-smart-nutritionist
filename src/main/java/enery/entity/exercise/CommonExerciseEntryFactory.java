package enery.entity.exercise;

import java.time.LocalDateTime;

/**
 * Creates standard exercise entries.
 */
public final class CommonExerciseEntryFactory
        implements ExerciseEntryFactory {

    @Override
    public ExerciseEntry create(
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDateTime dateTime) {

        return new ExerciseEntry(
                exerciseName,
                durationInMinutes,
                caloriesBurned,
                dateTime
        );
    }
}
