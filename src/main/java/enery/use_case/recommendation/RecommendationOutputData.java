package enery.use_case.recommendation;

import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;

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