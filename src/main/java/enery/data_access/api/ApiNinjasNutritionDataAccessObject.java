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
 * Retrieves nutrition information from API Ninjas.
 *
 * <p>This adapter implements an application-owned interface, so the rest of
 * the program does not depend directly on API Ninjas, OkHttp, or JSON. Another
 * provider can replace this implementation without changing the food-search
 * interactor.</p>
 */
public final class ApiNinjasNutritionDataAccessObject
        implements NutritionDataAccessInterface {

    private static final String BASE_URL =
            "https://api.api-ninjas.com/v1/nutrition";

    private static final String API_KEY_HEADER =
            "X-Api-Key";

    private static final double DEFAULT_SERVING_SIZE =
            100.0;

    private final OkHttpClient httpClient;
    private final String apiKey;

    /**
     * Creates the API adapter.
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
     * Searches for food nutrition information.
     *
     * <p>The query may include a quantity, for example {@code 1 cup rice} or
     * {@code 250 g chicken breast}. Without a quantity, the service commonly
     * returns nutrition values scaled to 100 grams.</p>
     *
     * @param query natural-language food query
     * @return matching food items
     */
    @Override
    public List<FoodItem> search(String query) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException(
                    "A food query is required.");
        }

        final HttpUrl baseUrl = HttpUrl.parse(BASE_URL);

        if (baseUrl == null) {
            throw new NutritionApiException(
                    "Invalid nutrition API URL.");
        }

        final HttpUrl requestUrl = baseUrl.newBuilder()
                .addQueryParameter("query", query.trim())
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
     * Converts external JSON objects into FoodItem entities.
     *
     * <p>Missing calories or protein values are represented using
     * {@link Double#NaN}. This prevents an unavailable premium value from
     * appearing to be a real zero.</p>
     *
     * @param json JSON response
     * @return parsed food items
     */
    private static List<FoodItem> parseResults(
            String json) {

        try {
            final JSONArray responseArray =
                    new JSONArray(json);

            final List<FoodItem> results =
                    new ArrayList<>();

            for (int index = 0;
                 index < responseArray.length();
                 index++) {

                final JSONObject object =
                        responseArray.getJSONObject(index);

                results.add(new FoodItem(
                        object.optString(
                                "name",
                                "Unknown food"),
                        object.optDouble(
                                "serving_size_g",
                                DEFAULT_SERVING_SIZE),
                        getOptionalDouble(
                                object,
                                "calories"),
                        getOptionalDouble(
                                object,
                                "protein_g"),
                        object.optDouble(
                                "carbohydrates_total_g",
                                0.0),
                        object.optDouble(
                                "fat_total_g",
                                0.0),
                        object.optDouble(
                                "fiber_g",
                                0.0),
                        object.optDouble(
                                "sugar_g",
                                0.0),
                        object.optDouble(
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

    /**
     * Reads an optional numeric JSON property.
     *
     * @param object JSON object
     * @param propertyName property to read
     * @return property value, or NaN when unavailable
     */
    private static double getOptionalDouble(
            JSONObject object,
            String propertyName) {

        if (!object.has(propertyName)
                || object.isNull(propertyName)) {

            return Double.NaN;
        }

        return object.optDouble(
                propertyName,
                Double.NaN
        );
    }
}
