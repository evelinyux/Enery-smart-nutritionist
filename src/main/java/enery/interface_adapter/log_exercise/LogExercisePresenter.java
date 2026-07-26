package enery.interface_adapter.log_exercise;

import enery.entity.ExerciseEntry;
import enery.use_case.log_exercise.LogExerciseOutputBoundary;
import enery.use_case.log_exercise.LogExerciseOutputData;

/**
 * Presenter for the log-exercise use case.
 */
public final class LogExercisePresenter
        implements LogExerciseOutputBoundary {

    private final LogExerciseViewModel viewModel;

    public LogExercisePresenter(
            LogExerciseViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            LogExerciseOutputData outputData) {

        final LogExerciseState state =
                viewModel.getState();

        final ExerciseEntry entry =
                outputData.getExerciseEntry();

        state.setErrorMessage("");
        state.setSuccessMessage(
                entry.getExerciseName()
                        + " was logged successfully."
        );

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final LogExerciseState state =
                viewModel.getState();

        state.setSuccessMessage("");
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
