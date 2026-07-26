package enery.interface_adapter.search_exercise;

import enery.use_case.search_exercise.SearchExerciseInputBoundary;
import enery.use_case.search_exercise.SearchExerciseInputData;

/**
 * Controller for searching exercise calorie information.
 */
public final class SearchExerciseController {

    private final SearchExerciseInputBoundary interactor;

    public SearchExerciseController(
            SearchExerciseInputBoundary interactor) {

        this.interactor = interactor;
    }

    public void execute(
            String activityQuery,
            double weightInPounds,
            int durationInMinutes) {

        final SearchExerciseInputData inputData =
                new SearchExerciseInputData(
                        activityQuery,
                        weightInPounds,
                        durationInMinutes
                );

        interactor.execute(inputData);
    }
}
