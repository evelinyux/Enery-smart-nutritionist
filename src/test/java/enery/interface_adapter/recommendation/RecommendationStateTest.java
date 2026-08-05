package enery.interface_adapter.recommendation;

import java.util.List;

import enery.entity.recipe.Recipe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationStateTest {

    @Test
    void copyConstructorCopiesFields() {
        final Recipe recipe = new Recipe(
                "1",
                "Salad",
                150.0,
                2.0,
                3.0,
                10.0,
                5.0,
                List.of("Lettuce"),
                "http://example.com",
                "A basic salad",
                "Mix the ingredients."
        );

        final RecommendationState original =
                new RecommendationState();

        original.setRecommendationError(
                "Failed to fetch"
        );

        original.setLoading(true);
        original.setRecommendedRecipes(
                List.of(recipe)
        );

        original.setRecommendedExercises(
                List.of("Walking (100 kcal)")
        );

        original.setRecipeTitles(
                List.of("Salad")
        );

        original.setRecipeUrls(
                List.of("http://example.com")
        );

        final RecommendationState copy =
                new RecommendationState(original);

        assertEquals(
                original.getRecommendationError(),
                copy.getRecommendationError()
        );

        assertEquals(
                original.isLoading(),
                copy.isLoading()
        );

        assertEquals(
                "Salad",
                copy.getRecommendedRecipes()
                        .get(0)
                        .getName()
        );

        assertEquals(
                List.of("Walking (100 kcal)"),
                copy.getRecommendedExercises()
        );

        assertNotSame(
                original.getRecommendedRecipes(),
                copy.getRecommendedRecipes()
        );

        assertNotSame(
                original.getRecommendedExercises(),
                copy.getRecommendedExercises()
        );
    }

    @Test
    void defaultConstructorInitializesLists() {
        final RecommendationState state =
                new RecommendationState();

        assertTrue(
                state.getRecommendedRecipes().isEmpty()
        );

        assertTrue(
                state.getRecommendedExercises().isEmpty()
        );

        assertTrue(
                state.getRecipeTitles().isEmpty()
        );

        assertTrue(
                state.getRecipeUrls().isEmpty()
        );
    }
}
