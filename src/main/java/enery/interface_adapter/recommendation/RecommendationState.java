package enery.interface_adapter.recommendation;

import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * State object representing the current UI state for the Recommendation feature.
 */
public class RecommendationState {

    // Store raw display strings instead of Domain Entities
    private List<String> recipeTitles = new ArrayList<>();
    private List<String> recipeUrls = new ArrayList<>();
    private List<String> workoutNames = new ArrayList<>();

    private String recommendationError = null;
    private boolean isLoading = false;

    // Copy Constructor
    public RecommendationState(RecommendationState copy) {
        this.recipeTitles = copy.recipeTitles != null ? new ArrayList<>(copy.recipeTitles) : new ArrayList<>();
        this.recipeUrls = copy.recipeUrls != null ? new ArrayList<>(copy.recipeUrls) : new ArrayList<>();
        this.workoutNames = copy.workoutNames != null ? new ArrayList<>(copy.workoutNames) : new ArrayList<>();
        this.recommendationError = copy.recommendationError;
        this.isLoading = copy.isLoading;
    }

    // Default Constructor
    public RecommendationState() {
    }

    // --- Getters and Setters ---

    public List<String> getRecipeTitles() {
        return recipeTitles;
    }

    public void setRecipeTitles(List<String> recipeTitles) {
        this.recipeTitles = recipeTitles;
    }
    public List<String> getRecipeUrls() {
        return recipeUrls;
    }

    public List<String> getRecommendedExercises() {
        return workoutNames;
    }

    public void setRecommendedExercises(List<String> recommendedExerciseNames) {
        this.workoutNames = recommendedExerciseNames;
    }

    public String getRecommendationError() {
        return recommendationError;
    }

    public void setRecommendationError(String recommendationError) {
        this.recommendationError = recommendationError;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }


}