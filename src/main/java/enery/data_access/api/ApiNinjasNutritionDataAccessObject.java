package enery.data_access.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import enery.entity.FoodItem;
import enery.use_case.search_food.NutritionDataAccessInterface;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Retrieves food nutrition information from the API Ninjas Nutrition API.
 *
 * <p>This class is an adapter between an external web service and the
 * application-owned {@link NutritionDataAccessInterface}. Other use cases
 * should depend on that interface rather than depending directly on this
 * concrete API implementation.</p>
 */
public final class ApiNinjasNutritionDataAccessObject
        implements NutritionDataAccessInterface {

    private static final String BASE_URL =
            "https://api.api-ninjas.com/v1/nutrition";

    private static final String API_KEY_HEADER =
            "X-Api-Key";

    private final OkHttpClient httpClient;
    private final String apiKey;

    /**
     * Creates a nutrition API adapter.
     *
     * @param httpClient reusable HTTP client
     * @param apiKey API Ninjas key
     */
    public ApiNinjasNutritionDataAccessObject(
            OkHttpClient httpClient,
            String apiKey) {

        if (httpClient == null) {
            throw new IllegalArgumentException(
                    "HTTP client is required.");
        }

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException(
                    "API Ninjas key is required.");
        }

        this.httpClient = httpClient;
        this.apiKey = apiKey;
    }

    /**
     * Searches for nutrition data matching a natural-language query.
     *
     * @param query food description, optionally including quantity
     * @return converted application food entities
     */
    @Override
    public List<FoodItem> search(String query) {
        final HttpUrl baseUrl = HttpUrl.parse(BASE_URL);

        if (baseUrl == null) {
            throw new NutritionApiException(
                    "Invalid nutrition API URL.");
        }

        final HttpUrl requestUrl = baseUrl.newBuilder()
                .addQueryParameter("query", query)
                .build();

        final Request request = new Request.Builder()
                .url(requestUrl)
                .header(API_KEY_HEADER, apiKey)
                .get()
                .build();

        try (Response response =
                     httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new NutritionApiException(
                        "Nutrition API request failed with status "
                                + response.code() + ".");
            }

            final ResponseBody responseBody =
                    response.body();

            if (responseBody == null) {
                throw new NutritionApiException(
                        "Nutrition API returned an empty response.");
            }

            return parseResults(responseBody.string());
        }
        catch (IOException exception) {
            throw new NutritionApiException(
                    "Unable to connect to the nutrition API.",
                    exception);
        }
    }

    /**
     * Converts API JSON into the application's FoodItem entities.
     *
     * <p>{@code optDouble} is used because some fields may be unavailable
     * depending on the API account or response. Missing values default to
     * zero instead of crashing the entire search.</p>
     *
     * @param json JSON response returned by the service
     * @return parsed food items
     */
    private static List<FoodItem> parseResults(String json) {
        try {
            final JSONArray responseArray =
                    new JSONArray(json);

            final List<FoodItem> results =
                    new ArrayList<>();

            for (int index = 0;
                 index < responseArray.length();
                 index++) {

                final JSONObject item =
                        responseArray.getJSONObject(index);

                results.add(new FoodItem(
                        item.optString(
                                "name",
                                "Unknown food"),
                        item.optDouble(
                                "serving_size_g",
                                100.0),
                        item.optDouble(
                                "calories",
                                0.0),
                        item.optDouble(
                                "protein_g",
                                0.0),
                        item.optDouble(
                                "carbohydrates_total_g",
                                0.0),
                        item.optDouble(
                                "fat_total_g",
                                0.0),
                        item.optDouble(
                                "fiber_g",
                                0.0),
                        item.optDouble(
                                "sugar_g",
                                0.0),
                        item.optDouble(
                                "sodium_mg",
                                0.0)
                ));
            }

            return List.copyOf(results);
        }
        catch (JSONException exception) {
            throw new NutritionApiException(
                    "Nutrition API returned invalid JSON.",
                    exception);
        }
    }
}
