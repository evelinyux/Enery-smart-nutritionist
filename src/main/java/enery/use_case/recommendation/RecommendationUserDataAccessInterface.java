package enery.use_case.recommendation;


import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;
import enery.entity.User;

import java.util.List;

public interface RecommendationUserDataAccessInterface {
    User getUser(String username);
    List<Recipe> fetchCandidateRecipes(int targetCalories, List<String> dietaryRestrictions);
    List<ExerciseSearchResult> fetchCandidateExercises(int targetCaloriesToBurn);
}
