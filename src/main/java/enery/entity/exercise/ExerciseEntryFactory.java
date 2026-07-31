package enery.entity.exercise;

import java.time.LocalDate;

/**
 * Creates exercise entries.
 */
public interface ExerciseEntryFactory {

    ExerciseEntry create(
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDate date
    );
}
