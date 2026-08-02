package enery.use_case.tracking;

import java.time.LocalDate;
import java.util.List;

import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.MealEntry;

/**
 * Supplies meals and exercises within a date range.
 */
public interface TrackingDataAccessInterface {

    List<MealEntry> getMeals(
            String username,
            LocalDate startDate,
            LocalDate endDate);

    List<ExerciseEntry> getExercises(
            String username,
            LocalDate startDate,
            LocalDate endDate);
}
