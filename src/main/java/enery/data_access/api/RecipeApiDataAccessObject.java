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
//      this.httpClient = HttpClient.newHttpClient();
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
     * Helper method to fetch recipes with full calorie and macro range constraints.
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

            urlBuilder.append("max_calories=").append(remainingCalories);
            urlBuilder.append("&max_protein=").append(remainingProteins);
            urlBuilder.append("&max_carbs=").append(remainingCarbs);
            urlBuilder.append("&max_fat=").append(remainingFats);

            if (dietaryRestrictions != null && !dietaryRestrictions.isEmpty()) {
                final List<String> formattedFlags = dietaryRestrictions.stream()
                        .map(String::trim)
                        .filter(s -> !s.isBlank())
                        .map(s -> s.replace(" ", "-"))
                        .toList();

                if (!formattedFlags.isEmpty()) {
                    final String flags = String.join(",", formattedFlags);
                    urlBuilder.append("&dietary=").append(flags);
                }
            }

            urlBuilder.append("&per_page=7");
            final String finalUrl = urlBuilder.toString();
            System.out.println("[DEBUG RecipeDAO] Built Request URL: " + finalUrl);

            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(finalUrl))
                    .header("X-API-Key", apiKey)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            System.out.println("[DEBUG RecipeDAO] Sending HTTP Request via HttpClient...");

            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                final JSONObject jsonResponse = new JSONObject(response.body());
                final JSONArray recipeList = jsonResponse.optJSONArray("data");
                System.out.println("[DEBUG RecipeDAO] JSON Keys in response: " + jsonResponse.keySet());

//                if (recipeList != null) {
//                    System.out.println("[DEBUG RecipeDAO] Extracted Recipe List Size: " + recipeList.length());
//
//                    for (int i = 0; i < recipeList.length(); i++) {
//                        final JSONObject item = recipeList.getJSONObject(i);
//                        final String id = item.optString("id", "");
//
//                        if (!id.isBlank()) {
//                            // --- Clean Architecture: Fetch complete details via getRecipeById ---
//                            Recipe fullRecipe = getRecipeById(id);
//                            if (fullRecipe != null) {
//                                recipes.add(fullRecipe);
//                            }
//                        }
//                    }
//                }
                if (recipeList != null) {
                    System.out.println("[DEBUG RecipeDAO] Extracted Recipe List Size: " + recipeList.length());

                    for (int i = 0; i < recipeList.length(); i++) {
                        final JSONObject item = recipeList.getJSONObject(i);
                        final String id = item.optString("id", "");

                        if (!id.isBlank()) {
                            // Try fetching detailed recipe first
                            Recipe fullRecipe = getRecipeById(id);

                            if (fullRecipe != null) {
                                recipes.add(fullRecipe);
                            } else {
                                // FALLBACK: If getRecipeById fails or returns null, construct summary recipe from list payload
                                final String name = item.optString("name", "Unnamed Recipe");
                                final String description = item.optString("description", "");

                                double calories = 0.0, protein = 0.0, sugar = 0.0, carbs = 0.0, fat = 0.0;
                                final JSONObject summary = item.optJSONObject("nutrition_summary");
                                if (summary != null) {
                                    calories = summary.optDouble("calories", 0.0);
                                    protein = summary.optDouble("protein_g", 0.0);
                                    carbs = summary.optDouble("carbohydrates_g", 0.0);
                                    fat = summary.optDouble("fat_g", 0.0);
                                }

                                recipes.add(new Recipe(
                                        id, name, calories, protein, sugar, carbs, fat,
                                        new ArrayList<>(), "https://recipe-api.com/recipes/" + id, description, ""
                                ));
                            }
                        }
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

    /**
     * Fetches full details for a recipe by ID and returns a fully populated Recipe entity.
     */
    public Recipe getRecipeById(String recipeId) {
        try {
            final String detailUrl = BASE_URL + "/" + recipeId;
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(detailUrl))
                    .header("X-API-Key", apiKey)
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                final JSONObject json = new JSONObject(response.body());
                final JSONObject item = json.optJSONObject("data") != null ? json.optJSONObject("data") : json;

                final String id = item.optString("id", recipeId);
                final String name = item.optString("name", "Unnamed Recipe");
                final String description = item.optString("description", "");

                // Extract Nutrition (Handles both detail endpoint and search endpoint keys)
                double calories = 0.0, protein = 0.0, sugar = 0.0, carbs = 0.0, fat = 0.0;
                JSONObject summary = item.optJSONObject("nutrition_summary");
                if (summary == null && item.optJSONObject("nutrition") != null) {
                    summary = item.optJSONObject("nutrition").optJSONObject("per_serving");
                }

                if (summary != null) {
                    calories = summary.optDouble("calories", 0.0);
                    protein = summary.optDouble("protein_g", 0.0);
                    carbs = summary.optDouble("carbohydrates_g", 0.0);
                    fat = summary.optDouble("fat_g", 0.0);
                    sugar = summary.optDouble("sugar_g", 0.0);
                }

                // Extract Ingredients with Quantity & Unit (Original Nested Loop)
                final List<String> ingredients = new ArrayList<>();
                final JSONArray ingredientGroups = item.optJSONArray("ingredients");
                if (ingredientGroups != null) {
                    for (int j = 0; j < ingredientGroups.length(); j++) {
                        JSONObject group = ingredientGroups.optJSONObject(j);
                        if (group != null) {
                            JSONArray items = group.optJSONArray("items");
                            if (items != null) {
                                for (int k = 0; k < items.length(); k++) {
                                    JSONObject ingObj = items.optJSONObject(k);
                                    if (ingObj != null) {
                                        String ingName = ingObj.optString("name", "");
                                        double qty = ingObj.isNull("amount") ? 0.0 : ingObj.optDouble("amount", 0.0);
                                        String unit = ingObj.optString("unit", "");
                                        String prep = ingObj.optString("preparation", "");

                                        StringBuilder line = new StringBuilder();

                                        if (qty > 0) {
                                            if (qty == (int) qty) {
                                                line.append((int) qty).append(" ");
                                            } else {
                                                line.append(qty).append(" ");
                                            }
                                        }

                                        if (unit != null && !unit.isBlank() && !"null".equalsIgnoreCase(unit)) {
                                            line.append(unit).append(" ");
                                        }

                                        line.append(ingName);

                                        if (prep != null && !prep.isBlank() && !"null".equalsIgnoreCase(prep)) {
                                            line.append(" (").append(prep).append(")");
                                        }

                                        if (!line.toString().isBlank()) {
                                            ingredients.add(line.toString());
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                // Extract Instructions (Original Nested Step Parser)
                StringBuilder instructionsBuilder = new StringBuilder();
                final JSONArray instructionsArray = item.optJSONArray("instructions");
                if (instructionsArray != null) {
                    for (int j = 0; j < instructionsArray.length(); j++) {
                        JSONObject stepObj = instructionsArray.optJSONObject(j);
                        if (stepObj != null) {
                            int stepNum = stepObj.optInt("step_number", j + 1);
                            String stepText = stepObj.optString("text", "");
                            if (!stepText.isBlank()) {
                                instructionsBuilder.append(stepNum).append(". ").append(stepText).append("\n");
                            }
                        }
                    }
                }

                final String instructions = instructionsBuilder.toString().trim();
                final String sourceUrl = "https://recipe-api.com/recipes/" + id;

                return new Recipe(
                        id, name, calories, protein, sugar, carbs, fat,
                        ingredients, sourceUrl, description, instructions
                );
            }
        } catch (Exception e) {
            System.err.println("[DEBUG RecipeDAO] Exception in getRecipeById:");
            e.printStackTrace();
        }
        return null;
    }}