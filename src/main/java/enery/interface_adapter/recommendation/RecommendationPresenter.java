package enery.interface_adapter.recommendation;

import enery.use_case.recommendation.RecommendationOutputBoundary;
import enery.use_case.recommendation.RecommendationOutputData;

/**
 * Presenter for the Recommendation use case.
 * Formats output data from the Interactor and updates the ViewModel/State for the View.
 */
public class RecommendationPresenter implements RecommendationOutputBoundary {

    private final RecommendationViewModel viewModel;

    public RecommendationPresenter(RecommendationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RecommendationOutputData response) {
        // Get current state (or clone existing state)
        final RecommendationState state = viewModel.getState();

        // Update state with newly recommended recipes and exercises
        state.setRecommendedRecipes(response.getRecommendedRecipes());
        state.setRecommendedExercises(response.getRecommendedExercises());
        state.setRecommendationError(null);
        state.setLoading(false);

        // Notify View of state update
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final RecommendationState state = viewModel.getState();

        // Set error message and reset loading flag
        state.setRecommendationError(error);
        state.setLoading(false);

        // Notify View of failure state
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }
}