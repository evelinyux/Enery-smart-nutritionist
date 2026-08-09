package enery.data_access.tracking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import enery.data_access.in_memory.InMemoryExerciseDataAccessObject;
import enery.data_access.in_memory.InMemoryMealDataAccessObject;
import enery.entity.exercise.ExerciseEntry;
import enery.entity.meal.MealEntry;
import enery.use_case.tracking.TrackingDataAccessInterface;

/**
 * Combines meal and exercise repositories for tracking.
 *
 * <p>The existing repositories retrieve one date at a time. This adapter loops
 * through the requested date range so the tracking interactor can support both
 * daily and weekly summaries.</p>
 */
public final class DailyTrackingDataAccessObject
        implements TrackingDataAccessInterface {

    private final InMemoryMealDataAccessObject mealDataAccessObject;
    private final InMemoryExerciseDataAccessObject
            exerciseDataAccessObject;

    public DailyTrackingDataAccessObject(
            InMemoryMealDataAccessObject mealDataAccessObject,
            InMemoryExerciseDataAccessObject exerciseDataAccessObject) {

        this.mealDataAccessObject = mealDataAccessObject;
        this.exerciseDataAccessObject = exerciseDataAccessObject;
    }

    @Override
    public List<MealEntry> getMeals(
            String username,
            LocalDate startDate,
            LocalDate endDate) {

        final List<MealEntry> results =
                new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            results.addAll(
                    mealDataAccessObject.getMeals(
                            username,
                            currentDate
                    )
            );

            currentDate = currentDate.plusDays(1);
        }

        return List.copyOf(results);
    }

    @Override
    public List<ExerciseEntry> getExercises(
            String username,
            LocalDate startDate,
            LocalDate endDate) {

        final List<ExerciseEntry> results =
                new ArrayList<>();

        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            results.addAll(
                    exerciseDataAccessObject.getExercises(
                            username,
                            currentDate
                    )
            );

            currentDate = currentDate.plusDays(1);
        }

        return List.copyOf(results);
    }
}
