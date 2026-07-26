package enery.use_case.log_exercise;

/**
 * Output boundary for logging exercise.
 */
public interface LogExerciseOutputBoundary {

    void prepareSuccessView(LogExerciseOutputData outputData);

    void prepareFailView(String errorMessage);
}
