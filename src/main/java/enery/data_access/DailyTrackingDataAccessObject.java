package enery.data_access;

import java.time.LocalDate;
import java.util.List;

import enery.entity.ExerciseEntry;
import enery.entity.MealEntry;
import enery.use_case.tracking.TrackingDataAccessInterface;

/**
 * Combines the meal and exercise repositories for daily tracking.
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
        this.exerciseDataAccessObject =
                exerciseDataAccessObject;
    }

    @Override
    public List<MealEntry> getMeals(
            String username,
            LocalDate date) {

        return mealDataAccessObject.getMeals(
                username,
                date);
    }

    @Override
    public List<ExerciseEntry> getExercises(
            String username,
            LocalDate date) {

        return exerciseDataAccessObject.getExercises(
                username,
                date);
    }
}
