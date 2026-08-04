package enery.use_case.search_exercise;

import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;

/**
 * Data-access operations required to search calories burned.
 */
public interface CaloriesBurnedDataAccessInterface {

    List<ExerciseSearchResult> search(
            String activityQuery,
            double weightInPounds,
            int durationInMinutes
    );
}
