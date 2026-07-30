package enery.entity.exercise;

import java.time.LocalDateTime;

/**
 * Creates exercise entries.
 */
public interface ExerciseEntryFactory {

    ExerciseEntry create(
            String exerciseName,
            int durationInMinutes,
            double caloriesBurned,
            LocalDateTime dateTime
    );
}
