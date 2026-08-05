package enery.interface_adapter.recommendation;

import enery.entity.recipe.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * State object representing the current UI state for the Recommendation feature.
 */
public class RecommendationState {

    private List<String> recipeTitles = new ArrayList<>();
    private List<String> recipeUrls = new ArrayList<>();
    private List<String> workoutNames = new ArrayList<>();

    // Added to give RecommendationView access to full Recipe entities for click-to-view navigation
    private List<Recipe> recommendedRecipes = new ArrayList<>();

    private String recommendationError = null;
    private boolean isLoading = false;

    // Copy Constructor
    public RecommendationState(RecommendationState copy) {
        this.recipeTitles = copy.recipeTitles != null ? new ArrayList<>(copy.recipeTitles) : new ArrayList<>();
        this.recipeUrls = copy.recipeUrls != null ? new ArrayList<>(copy.recipeUrls) : new ArrayList<>();
        this.workoutNames = copy.workoutNames != null ? new ArrayList<>(copy.workoutNames) : new ArrayList<>();
        this.recommendedRecipes = copy.recommendedRecipes != null ? new ArrayList<>(copy.recommendedRecipes) : new ArrayList<>();
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

    public void setRecipeUrls(List<String> recipeUrls) {
        this.recipeUrls = recipeUrls;
    }

    public List<String> getRecommendedExercises() {
        return workoutNames;
    }

    public void setRecommendedExercises(List<String> recommendedExerciseNames) {
        this.workoutNames = recommendedExerciseNames;
    }

    public List<Recipe> getRecommendedRecipes() {
        return recommendedRecipes;
    }

    public void setRecommendedRecipes(List<Recipe> recommendedRecipes) {
        this.recommendedRecipes = recommendedRecipes;
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