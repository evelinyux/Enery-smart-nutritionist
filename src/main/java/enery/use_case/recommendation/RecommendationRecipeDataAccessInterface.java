package enery.use_case.recommendation;

import enery.entity.recipe.Recipe;
import java.util.List;

public interface RecommendationRecipeDataAccessInterface {
    List<Recipe> fetchCandidateRecipes(int targetCalories, List<String> dietaryRestrictions);
}