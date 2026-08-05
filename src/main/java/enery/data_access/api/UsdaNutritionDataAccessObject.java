package enery.data_access.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import enery.entity.meal.FoodItem;
import enery.use_case.search_food.NutritionDataAccessInterface;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Retrieves searchable food and nutrient data from USDA FoodData Central.
 *
 * <p>This adapter implements the application-owned
 * {@link NutritionDataAccessInterface}. Therefore, controllers, presenters,
 * interactors, and views do not depend directly on USDA.</p>
 */
public final class UsdaNutritionDataAccessObject
        implements NutritionDataAccessInterface {

    private static final String SEARCH_URL =
            "https://api.nal.usda.gov/fdc/v1/foods/search";

    private static final int PAGE_SIZE = 10;

    private static final double DEFAULT_SERVING_SIZE_GRAMS =
            100.0;

    private final OkHttpClient httpClient;
    private final String apiKey;

    /**
     * Creates a USDA nutrition API adapter.
     *
     * @param httpClient reusable HTTP client
     * @param apiKey data.gov API key
     */
    public UsdaNutritionDataAccessObject(
            OkHttpClient httpClient,
            String apiKey) {

        if (httpClient == null) {
            throw new IllegalArgumentException(
                    "HTTP client is required.");
        }

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException(
                    "USDA API key is required.");
        }

        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    /**
     * Searches USDA FoodData Central for matching foods.
     *
     * @param query food-name search text
     * @return matching food items
     */
    @Override
    public List<FoodItem> search(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "A food query is required.");
        }

        final HttpUrl baseUrl = HttpUrl.parse(SEARCH_URL);

        if (baseUrl == null) {
            throw new NutritionApiException(
                    "Invalid USDA API URL.");
        }

        final HttpUrl requestUrl = baseUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .addQueryParameter("query", query.trim())
                .addQueryParameter(
                        "pageSize",
                        Integer.toString(PAGE_SIZE))
                .build();

        final Request request = new Request.Builder()
                .url(requestUrl)
                .get()
                .build();

        try (Response response =
                     httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new NutritionApiException(
                        "USDA nutrition request failed with status "
                                + response.code() + ".");
            }

            final ResponseBody responseBody =
                    response.body();

            if (responseBody == null) {
                throw new NutritionApiException(
                        "USDA returned an empty response.");
            }

            return parseResults(responseBody.string());
        }
        catch (IOException exception) {
            throw new NutritionApiException(
                    "Unable to connect to USDA FoodData Central.",
                    exception);
        }
    }

    /**
     * Converts USDA search results into FoodItem entities.
     *
     * <p>Search-result nutrient values are generally expressed per 100 grams,
     * so the entity uses 100 grams as its serving size.</p>
     *
     * @param json USDA response body
     * @return converted food entities
     */
    private static List<FoodItem> parseResults(
            String json) {

        try {
            final JSONObject root =
                    new JSONObject(json);

            final JSONArray foods =
                    root.optJSONArray("foods");

            if (foods == null) {
                return List.of();
            }

            final List<FoodItem> results =
                    new ArrayList<>();

            for (int index = 0;
                 index < foods.length();
                 index++) {

                final JSONObject food =
                        foods.getJSONObject(index);

                final JSONArray nutrients =
                        food.optJSONArray("foodNutrients");

                results.add(new FoodItem(
                        food.optString(
                                "description",
                                "Unknown food"),
                        DEFAULT_SERVING_SIZE_GRAMS,
                        findNutrient(
                                nutrients,
                                "Energy",
                                "KCAL"),
                        findNutrient(
                                nutrients,
                                "Protein",
                                "G"),
                        findNutrient(
                                nutrients,
                                "Carbohydrate, by difference",
                                "G"),
                        findNutrient(
                                nutrients,
                                "Total lipid (fat)",
                                "G"),
                        findNutrient(
                                nutrients,
                                "Fiber, total dietary",
                                "G"),
                        findNutrient(
                                nutrients,
                                "Sugars, total including NLEA",
                                "G"),
                        findNutrient(
                                nutrients,
                                "Sodium, Na",
                                "MG")
                ));
            }

            return List.copyOf(results);
        }
        catch (JSONException exception) {
            throw new NutritionApiException(
                    "USDA returned invalid JSON.",
                    exception);
        }
    }

    /**
     * Finds a nutrient value by nutrient name and unit.
     *
     * @param nutrients USDA nutrient array
     * @param nutrientName expected nutrient name
     * @param unitName expected unit
     * @return nutrient value, or zero if unavailable
     */
    private static double findNutrient(
            JSONArray nutrients,
            String nutrientName,
            String unitName) {

        if (nutrients == null) {
            return 0.0;
        }

        for (int index = 0;
             index < nutrients.length();
             index++) {

            final JSONObject nutrient =
                    nutrients.optJSONObject(index);

            if (nutrient == null) {
                continue;
            }

            final String name =
                    nutrient.optString("nutrientName");

            final String unit =
                    nutrient.optString("unitName");

            if (nutrientName.equalsIgnoreCase(name)
                    && unitName.equalsIgnoreCase(unit)) {

                return nutrient.optDouble(
                        "value",
                        0.0
                );
            }
        }

        return 0.0;
    }
}
