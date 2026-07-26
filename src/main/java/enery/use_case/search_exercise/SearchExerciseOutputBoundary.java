package enery.use_case.search_exercise;

/**
 * Output boundary for exercise searches.
 */
public interface SearchExerciseOutputBoundary {

    void prepareSuccessView(
            SearchExerciseOutputData outputData);

    void prepareFailView(String errorMessage);
}
