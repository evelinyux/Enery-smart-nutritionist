package enery.interface_adapter.recommendation;

import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;
import enery.interface_adapter.recommendation.RecommendationState;
import enery.use_case.recommendation.RecommendationOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecommendationPresenterTest {

    private RecommendationViewModel viewModel;
    private RecommendationPresenter presenter;

    @BeforeEach
    void setUp() {
        viewModel = new RecommendationViewModel();
        presenter = new RecommendationPresenter(viewModel);
    }

    @Test
    @DisplayName("prepareSuccessView - Updates ViewModel state with recommendations and clears errors")
    void prepareSuccessView_UpdatesViewModelState() {
        // Arrange
        final Recipe sampleRecipe = new Recipe(
                "r1", "Oatmeal", 250.0, 5.0, 40.0, 10.0, List.of("Oats"), "http://example.com"
        );
        final ExerciseSearchResult sampleExercise = new ExerciseSearchResult("Running", 300.0);

        final RecommendationOutputData outputData = new RecommendationOutputData(
                List.of(sampleRecipe),
                List.of(sampleExercise)
        );

        // Act
        presenter.prepareSuccessView(outputData);

        // Assert
        final RecommendationState state = viewModel.getState();
        assertEquals(1, state.getRecommendedRecipes().size());
        assertEquals("Oatmeal", state.getRecommendedRecipes().get(0).getName());
        assertEquals(1, state.getRecommendedExercises().size());
        assertEquals("Running", state.getRecommendedExercises().get(0).getExerciseName());
        assertNull(state.getRecommendationError());
        assertFalse(state.isLoading());
    }

    @Test
    @DisplayName("prepareFailView - Sets error message on ViewModel state")
    void prepareFailView_SetsErrorMessageInState() {
        // Arrange
        final String errorMessage = "User account was not found.";

        // Act
        presenter.prepareFailView(errorMessage);

        // Assert
        final RecommendationState state = viewModel.getState();
        assertEquals("User account was not found.", state.getRecommendationError());
        assertFalse(state.isLoading());
    }
}