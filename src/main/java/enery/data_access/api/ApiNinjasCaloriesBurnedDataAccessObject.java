package enery.data_access.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import enery.entity.ExerciseSearchResult;
import enery.use_case.search_exercise.CaloriesBurnedDataAccessInterface;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Searches exercise calorie information using the API Ninjas
 * Calories Burned API.
 */
public final class ApiNinjasCaloriesBurnedDataAccessObject
        implements CaloriesBurnedDataAccessInterface {

    private static final String BASE_URL =
            "https://api.api-ninjas.com/v1/caloriesburned";

    private static final String API_KEY_HEADER = "X-Api-Key";

    private final OkHttpClient httpClient;
    private final String apiKey;

    /**
     * Creates an API data-access object.
     *
     * @param httpClient reusable HTTP client
     * @param apiKey API Ninjas API key
     */
    public ApiNinjasCaloriesBurnedDataAccessObject(
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

    @Override
    public List<ExerciseSearchResult> search(
            String activityQuery,
            double weightInPounds,
            int durationInMinutes) {

        final HttpUrl baseUrl = HttpUrl.parse(BASE_URL);

        if (baseUrl == null) {
            throw new ExerciseApiException(
                    "Invalid Calories Burned API URL.");
        }

        final HttpUrl url = baseUrl.newBuilder()
                .addQueryParameter("activity", activityQuery)
                .addQueryParameter(
                        "weight",
                        Double.toString(weightInPounds))
                .addQueryParameter(
                        "duration",
                        Integer.toString(durationInMinutes))
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .header(API_KEY_HEADER, apiKey)
                .get()
                .build();

        try (Response response =
                     httpClient.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new ExerciseApiException(
                        "Exercise API request failed with status "
                                + response.code() + ".");
            }

            final ResponseBody responseBody = response.body();

            if (responseBody == null) {
                throw new ExerciseApiException(
                        "Exercise API returned an empty response.");
            }

            return parseResults(responseBody.string());
        }
        catch (IOException exception) {
            throw new ExerciseApiException(
                    "Unable to connect to the exercise API.",
                    exception);
        }
    }

    /**
     * Converts the API JSON response into application entities.
     *
     * @param json response body returned by API Ninjas
     * @return converted exercise results
     */
    private static List<ExerciseSearchResult> parseResults(
            String json) {

        final JSONArray array = new JSONArray(json);
        final List<ExerciseSearchResult> results =
                new ArrayList<>();

        for (int index = 0; index < array.length(); index++) {
            final JSONObject object =
                    array.getJSONObject(index);

            final ExerciseSearchResult result =
                    new ExerciseSearchResult(
                            object.getString("name"),
                            object.getDouble(
                                    "calories_per_hour"),
                            object.getInt(
                                    "duration_minutes"),
                            object.getDouble(
                                    "total_calories")
                    );

            results.add(result);
        }

        return List.copyOf(results);
    }
}
