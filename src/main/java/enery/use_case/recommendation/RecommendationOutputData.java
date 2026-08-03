package enery.use_case.recommendation;

import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;
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

    //TODO: Is the response to the API call automacitally a Recipe object? Dont think so.
    // SHould it be made into one?


    public List<String> getRecommendedRecipeTitles() {
        String jsonString = this.getRecommendedRecipes().toString();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray arr = obj.getJSONArray("name");
        return Arrays.asList(arr.toString().split(","));
    }

    public List<ExerciseSearchResult> getRecommendedExercises() {return this.recommendedExercises;}
}