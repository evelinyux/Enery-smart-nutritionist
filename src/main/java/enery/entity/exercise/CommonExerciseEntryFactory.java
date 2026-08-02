package enery.entity.exercise;

import java.time.LocalDate;

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
            LocalDate date) {

        return new ExerciseEntry(
                exerciseName,
                durationInMinutes,
                caloriesBurned,
                date
        );
    }
}
