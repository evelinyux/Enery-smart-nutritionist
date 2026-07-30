package enery.use_case.recommendation;


import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.recipe.Recipe;
import enery.entity.user.User;

import java.util.List;

public interface RecommenderUserDataAccessInterface {
    User getUser(String username);
    List<Recipe> fetchCandidateRecipes(int targetCalories, List<String> dietaryRestrictions);
    List<ExerciseSearchResult> fetchCandidateExercises(int targetCaloriesToBurn);
}
