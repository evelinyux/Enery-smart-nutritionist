package enery.data_access.api;

import enery.entity.recipe.Recipe;
import enery.use_case.recommendation.RecommendationRecipeDataAccessInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URLEncoder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object that integrates with recipe-api.com to fetch candidate recipes
 * based on calorie constraints, macro limits, and dietary restrictions.
 */
public class RecipeApiDataAccessObject implements RecommendationRecipeDataAccessInterface {

    private static final String BASE_URL = "https://recipe-api.com/api/v1/recipes";
    private final String apiKey;
    private final HttpClient httpClient;

    /**
     * @param apiKey Your recipe-api.com API key (e.g., rapi_...)
     */
    public RecipeApiDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
//        this.httpClient = HttpClient.newHttpClient();
        this.httpClient = createInsecureHttpClient();
    }

    /**
     * Creates an HttpClient that trusts all SSL certificates to prevent PKIX path failures.
     */
    private static HttpClient createInsecureHttpClient() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());

            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
        } catch (Exception e) {
            System.err.println("Failed to build insecure HttpClient, falling back to default.");
            return HttpClient.newHttpClient();
        }
    }


    /**
     * Overloaded helper method to fetch recipes with full calorie and macro range constraints.
     */
    public List<Recipe> fetchCandidateRecipes(double remainingCalories,
                                              double remainingProteins,
                                              double remainingCarbs,
                                              double remainingFats,
                                              List<String> dietaryRestrictions) {
        System.out.println("[DEBUG RecipeDAO] Entered fetchCandidateRecipes()!");
        System.out.println("[DEBUG RecipeDAO] Parameters -> Cals: " + remainingCalories
                + ", Protein: " + remainingProteins
                + ", Carbs: " + remainingCarbs
                + ", Fat: " + remainingFats
                + ", Dietary: " + dietaryRestrictions);
        final List<Recipe> recipes = new ArrayList<>();

        try {
            final StringBuilder urlBuilder = new StringBuilder(BASE_URL);
            urlBuilder.append("?");

            // Set maximum calories for the meal recommendation
            urlBuilder.append("max_calories=").append(remainingCalories);

            urlBuilder.append("&max_protein=").append(remainingProteins);

            urlBuilder.append("&max_carbs=").append(remainingCarbs);

            urlBuilder.append("&max_fat=").append(remainingFats);

//            // Add comma-separated dietary flags if present
//            if (dietaryRestrictions != null && !dietaryRestrictions.isEmpty()) {
//                final String flags = String.join(",", dietaryRestrictions);
//                urlBuilder.append("&dietary=").append(URLEncoder.encode(flags, StandardCharsets.UTF_8));
//            }
            // Add comma-separated dietary flags if present
            if (dietaryRestrictions != null && !dietaryRestrictions.isEmpty()) {
                final List<String> formattedFlags = dietaryRestrictions.stream()
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .map(s -> s.replace(" ", "-")) // "Gluten Free" -> "Gluten-Free"
                        .toList();

                if (!formattedFlags.isEmpty()) {
                    final String flags = String.join(",", formattedFlags); // Joins with a clean literal comma
                    urlBuilder.append("&dietary=").append(flags);
                }
            }

            // Append page size limit to query parameters
            urlBuilder.append("&per_page=7");
            final String finalUrl = urlBuilder.toString();
            System.out.println("[DEBUG RecipeDAO] Built Request URL: " + finalUrl);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(urlBuilder.toString()))
                    .header("X-API-Key", apiKey)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            System.out.println("[DEBUG RecipeDAO] Sending HTTP Request via HttpClient...");

            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

//            if (response.statusCode() == 200) {
//                final JSONObject jsonResponse = new JSONObject(response.body());
////                final JSONArray recipeList = jsonResponse.optJSONArray("data");
//                //re[lace ^ with belwo
//                JSONArray recipeList = jsonResponse.optJSONArray("data");
//                if (recipeList == null) {
//                    recipeList = jsonResponse.optJSONArray("results");
//                }
//                if (recipeList == null) {
//                    recipeList = jsonResponse.optJSONArray("recipes");
//                }
//                // instead ^^
//
//                if (recipeList != null) {
//                    for (int i = 0; i < recipeList.length(); i++) {
//                        final JSONObject item = recipeList.getJSONObject(i);
//
//                        final String id = item.optString("id", "r_" + i);
//                        final String name = item.optString("name", "Unnamed Recipe");
//                        final String description = item.optString("description", "");
//
//                        // Extract Nutrition
//                        double calories = 0.0;
//                        double protein = 0.0;
//                        double sugar = 0.0;
//                        double carbs = 0.0;
//                        double fat = 0.0;
            if (response.statusCode() == 200) {
                final JSONObject jsonResponse = new JSONObject(response.body());
                final JSONArray recipeList = jsonResponse.optJSONArray("data");
                System.out.println("[DEBUG RecipeDAO] JSON Keys in response: " + jsonResponse.keySet());

                if (recipeList != null) {
                    System.out.println("[DEBUG RecipeDAO] Extracted Recipe List Size: "
                            + (recipeList != null ? recipeList.length() : "NULL"));
                    for (int i = 0; i < recipeList.length(); i++) {
                        final JSONObject item = recipeList.getJSONObject(i);

                        final String id = item.optString("id", "r_" + i);
                        final String name = item.optString("name", "Unnamed Recipe");
                        final String description = item.optString("description", "");

                        // Extract Nutrition Summary returned by GET /api/v1/recipes
                        double calories = 0.0;
                        double protein = 0.0;
                        double sugar = 0.0;
                        double carbs = 0.0;
                        double fat = 0.0;

                        final JSONObject summary = item.optJSONObject("nutrition_summary");
                        if (summary != null) {
                            calories = summary.optDouble("calories", 0.0);
                            protein = summary.optDouble("protein_g", 0.0);
                            carbs = summary.optDouble("carbohydrates_g", 0.0);
                            fat = summary.optDouble("fat_g", 0.0);
                        }

                        final JSONObject nutrition = item.optJSONObject("nutrition");
                        if (nutrition != null) {
                            final JSONObject perServing = nutrition.optJSONObject("per_serving");
                            if (perServing != null) {
                                calories = perServing.optDouble("calories", 0.0);
                                protein = perServing.optDouble("protein", perServing.optDouble("protein_g", 0.0));
                                sugar = perServing.optDouble("sugar", perServing.optDouble("sugar_g", 0.0));
                                carbs = perServing.optDouble("carbohydrates", perServing.optDouble("carbs_g", 0.0));
                                fat = perServing.optDouble("fat", perServing.optDouble("fat_g", 0.0));
                            }
                        }

                        // Extract Ingredients
                        final List<String> ingredients = new ArrayList<>();
                        final JSONArray ingredientArray = item.optJSONArray("ingredients");
                        if (ingredientArray != null) {
                            for (int j = 0; j < ingredientArray.length(); j++) {
                                Object ingObj = ingredientArray.get(j);
                                if (ingObj instanceof JSONObject) {
                                    JSONObject ingJson = (JSONObject) ingObj;
                                    JSONArray items = ingJson.optJSONArray("items");
                                    if (items != null) {
                                        for (int k = 0; k < items.length(); k++) {
                                            ingredients.add(items.getJSONObject(k).optString("name", ""));
                                        }
                                    } else {
                                        ingredients.add(ingJson.optString("name", ""));
                                    }
                                } else if (ingObj instanceof String) {
                                    ingredients.add((String) ingObj);
                                }
                            }
                        }

                        // Extract Instructions
                        StringBuilder instructionsBuilder = new StringBuilder();
                        final JSONArray instructionsArray = item.optJSONArray("instructions");
                        if (instructionsArray != null) {
                            for (int j = 0; j < instructionsArray.length(); j++) {
                                Object step = instructionsArray.get(j);
                                if (step instanceof JSONObject) {
                                    instructionsBuilder.append(j + 1).append(". ")
                                            .append(((JSONObject) step).optString("text", ""))
                                            .append("\n");
                                } else {
                                    instructionsBuilder.append(j + 1).append(". ")
                                            .append(step.toString())
                                            .append("\n");
                                }
                            }
                        }
                        final String instructions = instructionsBuilder.toString();
                        final String sourceUrl = "https://recipe-api.com/recipes/" + id;

                        recipes.add(new Recipe(
                                id, name, calories, protein, sugar, carbs, fat,
                                ingredients, sourceUrl, description, instructions
                        ));
                    }
                }
            } else {
                System.err.println("Recipe API Request Failed! Status Code: " + response.statusCode());
                System.err.println("Response Body: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("[DEBUG RecipeDAO] EXCEPTION caught during API call:");
            e.printStackTrace();
        }
        System.out.println("[DEBUG RecipeDAO] Returning " + recipes.size() + " recipes to caller.");
        return recipes;
    }

}