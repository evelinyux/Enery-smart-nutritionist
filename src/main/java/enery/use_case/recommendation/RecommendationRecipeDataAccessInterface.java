//package enery.use_case.recommendation;
//
//import enery.entity.recipe.Recipe;
//import java.util.List;
//
//public interface RecommendationRecipeDataAccessInterface {
//    List<Recipe> fetchCandidateRecipes(int targetCalories, List<String> dietaryRestrictions);
//}
package enery.use_case.recommendation;

import enery.entity.recipe.Recipe;
import java.util.List;

public interface RecommendationRecipeDataAccessInterface {
    List<Recipe> fetchCandidateRecipes(double remainingCalories, double remainingProteins,
                                       double remainingCarbs, double remainingFats, List<String> dietaryRestrictions);
}