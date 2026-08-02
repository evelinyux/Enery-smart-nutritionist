package enery.use_case.recommendation;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.recipe.Recipe;

import java.util.List;

public class RecommenderOutputData {
    private final List<Recipe> recommendedRecipes;
    private final List<ExerciseSearchResult> recommendedExercises;

    public RecommenderOutputData(List<Recipe> recommendedRecipes,
                                 List<ExerciseSearchResult> recommendedExercises) {
        this.recommendedRecipes = recommendedRecipes;
        this.recommendedExercises = recommendedExercises;
    }
}
