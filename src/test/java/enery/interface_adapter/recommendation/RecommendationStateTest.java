package enery.interface_adapter.recommendation;

import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecommendationStateTest {

    @Test
    @DisplayName("Copy Constructor - Creates independent deep/shallow copies of fields")
    void copyConstructor_CopiesAllFieldsCorrectly() {
        // Arrange
        final RecommendationState originalState = getState();

        // Act
        final RecommendationState copiedState = new RecommendationState(originalState);

        // Assert
        assertEquals(originalState.getRecommendationError(), copiedState.getRecommendationError());
        assertEquals(originalState.isLoading(), copiedState.isLoading());
        assertEquals(1, copiedState.getRecommendedRecipes().size());
        assertEquals("Salad", copiedState.getRecommendedRecipes().get(0).getName());
        assertEquals(1, copiedState.getRecommendedExercises().size());
        assertEquals("Walking", copiedState.getRecommendedExercises().get(0).getExerciseName());
    }

    @NotNull
    private static RecommendationState getState() {
        final RecommendationState originalState = new RecommendationState();
        originalState.setRecommendationError("Failed to fetch");
        originalState.setLoading(true);

        final Recipe recipe = new Recipe("1", "Salad", 150.0, 2.0, 10.0, 5.0, List.of(), "http://example.com");
        final ExerciseSearchResult exercise = new ExerciseSearchResult("Walking", 100.0);

        originalState.setRecommendedRecipes(List.of(recipe));
        originalState.setRecommendedExercises(List.of(exercise));
        return originalState;
    }

    @Test
    @DisplayName("Default Constructor - Initializes lists safely without nulls")
    void defaultConstructor_InitializesEmptyLists() {
        // Arrange & Act
        final RecommendationState state = new RecommendationState();

        // Assert
        assertTrue(state.getRecommendedRecipes().isEmpty());
        assertTrue(state.getRecommendedExercises().isEmpty());
    }
}