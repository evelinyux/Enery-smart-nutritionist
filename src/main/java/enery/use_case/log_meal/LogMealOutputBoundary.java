package enery.use_case.log_meal;

/**
 * Output boundary for logging a meal.
 */
public interface LogMealOutputBoundary {

    void prepareSuccessView(LogMealOutputData outputData);

    void prepareFailView(String errorMessage);
}
