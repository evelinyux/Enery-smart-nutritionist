package enery.interface_adapter.search_food;

import java.util.List;

import enery.use_case.search_food.SearchFoodOutputBoundary;
import enery.use_case.search_food.SearchFoodOutputData;

/**
 * Converts food-search use-case output into view-model state.
 */
public final class SearchFoodPresenter
        implements SearchFoodOutputBoundary {

    private final SearchFoodViewModel viewModel;

    public SearchFoodPresenter(
            SearchFoodViewModel viewModel) {

        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            SearchFoodOutputData outputData) {

        final SearchFoodState state =
                viewModel.getState();

        state.setResults(outputData.getResults());
        state.setErrorMessage("");

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SearchFoodState state =
                viewModel.getState();

        state.setResults(List.of());
        state.setErrorMessage(errorMessage);

        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}
