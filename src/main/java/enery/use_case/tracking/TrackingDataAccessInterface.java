package enery.use_case.tracking;

import java.time.LocalDate;
import java.util.List;

import enery.entity.ExerciseEntry;
import enery.entity.MealEntry;

/**
 * Supplies meal and exercise entries needed for daily tracking.
 *
 * <p>A concrete implementation may read from separate meal and exercise
 * repositories, a database, or another persistent storage system.</p>
 */
public interface TrackingDataAccessInterface {

    List<MealEntry> getMeals(
            String username,
            LocalDate date);

    List<ExerciseEntry> getExercises(
            String username,
            LocalDate date);
}
