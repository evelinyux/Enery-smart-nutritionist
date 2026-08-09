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

    /**
     * Executes an exercise search.
     *
     * @param username logged-in username
     * @param activityQuery exercise name/query
     * @param durationInMinutes exercise duration
     */
    public void execute(
            String username,
            String activityQuery,
            int durationInMinutes) {

        final SearchExerciseInputData inputData =
                new SearchExerciseInputData(
                        username,
                        activityQuery,
                        durationInMinutes
                );

        interactor.execute(inputData);
    }
}
