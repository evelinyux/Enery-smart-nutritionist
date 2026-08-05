package enery.interface_adapter.recipe;

import enery.entity.recipe.Recipe;
import java.util.List;

public class RecipePresenter {
    private final RecipeViewModel recipeViewModel;

    public RecipePresenter(RecipeViewModel recipeViewModel) {
        this.recipeViewModel = recipeViewModel;
    }

    /**
     * Converts a Recipe entity into a formatted RecipeState and notifies the UI.
     */
    public void prepareRecipeView(Recipe recipe) {
        RecipeState state = new RecipeState(recipeViewModel.getState());

        state.setId(recipe.getId());
        state.setRecipeName(recipe.getName());
        state.setDescription(recipe.getDescription());
        state.setRecipeUrl(recipe.getRecipeUrl());

        // Formatting numeric nutrition fields to unit strings
        state.setCalories(String.format("%.0f kcal", recipe.getCalories()));
        state.setProtein(String.format("%.1fg", recipe.getProteinInGrams()));
        state.setCarbs(String.format("%.1fg", recipe.getCarbohydratesInGrams()));
        state.setFat(String.format("%.1fg", recipe.getFatInGrams()));
        state.setSugar(String.format("%.1fg", recipe.getSugarInGrams()));

        // Convert List<String> to multi-line bullet points for the view
        List<String> rawIngredients = recipe.getIngredients();
        if (rawIngredients != null && !rawIngredients.isEmpty()) {
            state.setIngredients("• " + String.join("\n• ", rawIngredients));
        } else {
            state.setIngredients("No ingredients provided.");
        }

        state.setInstructions(recipe.getInstructions() != null ? recipe.getInstructions() : "No instructions available.");
        state.setRecipeError(null);

        recipeViewModel.setState(state);
    }

    public void prepareFailView(String error) {
        RecipeState state = new RecipeState(recipeViewModel.getState());
        state.setRecipeError(error);
        recipeViewModel.setState(state);
    }
}