package enery.interface_adapter.log_meal;

import enery.entity.MealEntry;
import enery.use_case.log_meal.LogMealOutputBoundary;
import enery.use_case.log_meal.LogMealOutputData;

/**
 * Presenter for the log-meal use case.
 */
public final class LogMealPresenter
        implements LogMealOutputBoundary {

    private final LogMealViewModel logMealViewModel;

    public LogMealPresenter(
            LogMealViewModel logMealViewModel) {

        this.logMealViewModel = logMealViewModel;
    }

    @Override
    public void prepareSuccessView(
            LogMealOutputData outputData) {

        final LogMealState state =
                logMealViewModel.getState();

        final MealEntry mealEntry =
                outputData.getMealEntry();

        state.setErrorMessage("");
        state.setSuccessMessage(
                mealEntry.getFoodItem().getName()
                        + " was logged successfully."
        );

        logMealViewModel.setState(state);
        logMealViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LogMealState state =
                logMealViewModel.getState();

        state.setSuccessMessage("");
        state.setErrorMessage(errorMessage);

        logMealViewModel.setState(state);
        logMealViewModel.firePropertyChanged();
    }
}
