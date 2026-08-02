package enery.data_access.api;

import enery.entity.DailyLog;
import enery.entity.ExerciseSearchResult;
import enery.entity.Recipe;
import enery.entity.User;
import enery.use_case.recommendation.RecommendationUserDataAccessInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object that integrates with recipe-api.com to fetch candidate recipes
 * based on calorie constraints, macro limits, and dietary restrictions.
 */
public class RecipeApiDataAccessObject implements RecommendationUserDataAccessInterface {

    private static final String BASE_URL = "https://recipe-api.com/api/v1/recipes";
    private final String apiKey;
    private final HttpClient httpClient;

    /**
     * @param apiKey Your recipe-api.com API key (e.g., rapi_...)
     */
    public RecipeApiDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    /**
     * Overloaded helper method to fetch recipes with full calorie and macro range constraints.
     */
    public List<Recipe> fetchCandidateRecipesWithMacros(int maxCalories,
                                                        Double minFat,
                                                        Double maxFat,
                                                        List<String> dietaryRestrictions) {
        final List<Recipe> recipes = new ArrayList<>();

        try {
            final StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?");

            // Set maximum calories for the meal recommendation
            urlBuilder.append("max_calories=").append(maxCalories);

            // Add fat range parameters if provided
            if (minFat != null) {
                urlBuilder.append("&min_fat=").append(minFat);
            }
            if (maxFat != null) {
                urlBuilder.append("&max_fat=").append(maxFat);
            }

            // Add comma-separated dietary flags if present
            if (dietaryRestrictions != null && !dietaryRestrictions.isEmpty()) {
                final String flags = String.join(",", dietaryRestrictions);
                urlBuilder.append("&dietary=").append(URLEncoder.encode(flags, StandardCharsets.UTF_8));
            }

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlBuilder.toString()))
                    .header("X-API-Key", apiKey)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                final JSONObject jsonResponse = new JSONObject(response.body());
                final JSONArray results = jsonResponse.optJSONArray("data");

                // In case the API returns an array directly or an object wrapping "data"
                final JSONArray recipeList = results != null ? results : jsonResponse.optJSONArray("recipes");

                if (recipeList != null) {
                    for (int i = 0; i < recipeList.length(); i++) {
                        final JSONObject item = recipeList.getJSONObject(i);

                        final String id = item.optString("id", "r_" + i);
                        final String name = item.optString("name", "Unnamed Recipe");

                        // Extract per_serving nutrients
                        double calories = 0.0;
                        double protein = 0.0;
                        double carbs = 0.0;
                        double fat = 0.0;

                        final JSONObject nutrition = item.optJSONObject("nutrition");
                        if (nutrition != null) {
                            final JSONObject perServing = nutrition.optJSONObject("per_serving");
                            if (perServing != null) {
                                calories = perServing.optDouble("calories", 0.0);
                                protein = perServing.optDouble("protein", 0.0);
                                carbs = perServing.optDouble("carbohydrates", 0.0);
                                fat = perServing.optDouble("fat", 0.0);
                            }
                        }

                        // Extract ingredients list summary
                        final List<String> ingredients = new ArrayList<>();
                        final JSONArray ingredientGroups = item.optJSONArray("ingredients");
                        if (ingredientGroups != null) {
                            for (int j = 0; j < ingredientGroups.length(); j++) {
                                final JSONObject group = ingredientGroups.getJSONObject(j);
                                final JSONArray items = group.optJSONArray("items");
                                if (items != null) {
                                    for (int k = 0; k < items.length(); k++) {
                                        ingredients.add(items.getJSONObject(k).optString("name", ""));
                                    }
                                }
                            }
                        }

                        final String sourceUrl = "https://recipe-api.com/recipes/" + id;

                        recipes.add(new Recipe(id, name, calories, protein, carbs, fat, ingredients, sourceUrl));
                    }
                }
            } else {
                System.err.println("Recipe API error status code: " + response.statusCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipes;
    }

    // --- Implementation of RecommendationUserDataAccessInterface ---

    @Override
    public List<Recipe> fetchCandidateRecipes(int targetCalories, List<String> dietaryRestrictions) {
        // Calls the macro method with default fat parameters (or customized based on your needs)
        return fetchCandidateRecipesWithMacros(targetCalories, null, null, dietaryRestrictions);
    }

    @Override
    public List<ExerciseSearchResult> fetchCandidateExercises(int targetCalories) {
        // Return empty list or delegate to exercise DAO/Memory implementation
        return List.of();
    }

    @Override
    public User getUser(String username) {
        // Handled by User DAO
        return null;
    }
}