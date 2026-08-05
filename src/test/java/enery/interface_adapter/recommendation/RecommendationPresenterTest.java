package enery.interface_adapter.recommendation;

import java.util.List;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.recipe.Recipe;
import enery.use_case.recommendation.RecommendationOutputData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void prepareSuccessViewUpdatesState() {
        final Recipe recipe = new Recipe(
                "r1",
                "Oatmeal",
                250.0,
                5.0,
                2.0,
                40.0,
                10.0,
                List.of("Oats"),
                "http://example.com",
                "A bowl of oatmeal",
                "Cook oats with water."
        );

        final ExerciseSearchResult exercise =
                new ExerciseSearchResult(
                        "Running",
                        300.0,
                        60,
                        300.0
                );

        final RecommendationOutputData outputData =
                new RecommendationOutputData(
                        List.of(recipe),
                        List.of(exercise)
                );

        presenter.prepareSuccessView(outputData);

        final RecommendationState state =
                viewModel.getState();

        assertEquals(
                1,
                state.getRecommendedRecipes().size()
        );

        assertEquals(
                "Oatmeal",
                state.getRecommendedRecipes()
                        .get(0)
                        .getName()
        );

        assertEquals(
                List.of("Oatmeal"),
                state.getRecipeTitles()
        );

        assertEquals(
                List.of("http://example.com"),
                state.getRecipeUrls()
        );

        assertEquals(
                List.of("Running (300 kcal)"),
                state.getRecommendedExercises()
        );

        assertNull(state.getRecommendationError());
        assertFalse(state.isLoading());
    }

    @Test
    void prepareFailViewSetsError() {
        presenter.prepareFailView(
                "User account was not found."
        );

        final RecommendationState state =
                viewModel.getState();

        assertEquals(
                "User account was not found.",
                state.getRecommendationError()
        );

        assertFalse(state.isLoading());
    }
}
