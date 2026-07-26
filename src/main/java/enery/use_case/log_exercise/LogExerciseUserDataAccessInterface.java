package enery.use_case.log_exercise;

import java.time.LocalDate;
import java.util.List;

import enery.entity.ExerciseEntry;

/**
 * Data-access operations required by the log-exercise use case.
 */
public interface LogExerciseUserDataAccessInterface {

    void saveExercise(
            String username,
            ExerciseEntry exerciseEntry
    );

    List<ExerciseEntry> getExercises(
            String username,
            LocalDate date
    );
}
