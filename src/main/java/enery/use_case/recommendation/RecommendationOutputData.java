package enery.use_case.recommendation;

import enery.entity.exercise.ExerciseSearchResult;
import enery.entity.recipe.Recipe;
import org.json.*;

import java.util.Arrays;
import java.util.List;

public class RecommendationOutputData {
    private final List<Recipe> recommendedRecipes;
    private final List<ExerciseSearchResult> recommendedExercises;

    public RecommendationOutputData(List<Recipe> recommendedRecipes,
                                    List<ExerciseSearchResult> recommendedExercises) {
        this.recommendedRecipes = recommendedRecipes;
        this.recommendedExercises = recommendedExercises;
    }

    public List<Recipe> getRecommendedRecipes() {return this.recommendedRecipes;}

    public List<ExerciseSearchResult> getRecommendedExercises() {return this.recommendedExercises;}
}