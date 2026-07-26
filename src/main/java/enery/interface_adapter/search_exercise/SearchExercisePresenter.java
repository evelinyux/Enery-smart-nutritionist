package enery.interface_adapter.search_exercise;

import enery.use_case.search_exercise.SearchExerciseOutputBoundary;
import enery.use_case.search_exercise.SearchExerciseOutputData;

/**
 * Presenter for exercise-search results.
 */
public final class SearchExercisePresenter
        implements SearchExerciseOutputBoundary {

    private final SearchExerciseViewModel viewModel;

    public SearchExercisePresenter(
            SearchExerciseViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            SearchExerciseOutputData outputData) {

        final SearchExerciseState state =
                viewModel.getState();

        state.setResults(outputData.getResults());
        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SearchExerciseState state =
                viewModel.getState();

        state.setResults(java.util.List.of());
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
