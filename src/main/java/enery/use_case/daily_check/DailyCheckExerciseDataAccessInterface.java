package enery.use_case.daily_check;

import java.time.LocalDate;

/**
 * Data access interface for checking whether exercise
 * has been logged for a given date.
 */
public interface DailyCheckExerciseDataAccessInterface {

    /**
     * Returns whether the user has logged at least
     * one exercise on the given date.
     */
    boolean hasExercise(
            String username,
            LocalDate date
    );
}