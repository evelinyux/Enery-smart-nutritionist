package enery.data_access.api;

import enery.entity.meal.FoodItem;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApiNinjasNutritionDataAccessObjectTest {

    private static final String API_KEY = "test-key";

    @Test
    void constructorRejectsNullClient() {
        final IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> new ApiNinjasNutritionDataAccessObject(
                                null,
                                API_KEY
                        )
                );

        assertEquals(
                "HTTP client is required.",
                exception.getMessage()
        );
    }

    @Test
    void constructorRejectsNullKey() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ApiNinjasNutritionDataAccessObject(
                        client,
                        null
                )
        );
    }

    @Test
    void constructorRejectsBlankKey() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        assertThrows(
                IllegalArgumentException.class,
                () -> new ApiNinjasNutritionDataAccessObject(
                        client,
                        "   "
                )
        );
    }

    @Test
    void rejectsNullSearchQuery() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> dao.search(null)
        );
    }

    @Test
    void rejectsBlankSearchQuery() {
        final OkHttpClient client =
                mock(OkHttpClient.class);

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> dao.search("   ")
        );
    }

    @Test
    void successfulSearchParsesFood()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                [
                  {
                    "name": "banana",
                    "serving_size_g": 100.0,
                    "calories": 89.0,
                    "protein_g": 1.1,
                    "carbohydrates_total_g": 22.8,
                    "fat_total_g": 0.3,
                    "fiber_g": 2.6,
                    "sugar_g": 12.2,
                    "sodium_mg": 1.0
                  }
                ]
                """;

        final Response response =
                createResponse(
                        200,
                        "OK",
                        json
                );

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(response);

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("banana");

        assertEquals(1, results.size());
        assertNotNull(results.get(0));

        verify(client).newCall(any(Request.class));
        verify(call).execute();
    }

    @Test
    void missingOptionalNutritionValuesStillParses()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                [
                  {
                    "name": "Unknown Item"
                  }
                ]
                """;

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                json
                        )
                );

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("unknown");

        assertEquals(1, results.size());
        assertNotNull(results.get(0));
    }

    @Test
    void nullOptionalNutritionValuesStillParses()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        final String json = """
                [
                  {
                    "name": "test",
                    "calories": null,
                    "protein_g": null
                  }
                ]
                """;

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                json
                        )
                );

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final List<FoodItem> results =
                dao.search("test");

        assertEquals(1, results.size());
    }

    @Test
    void invalidJsonThrowsNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                200,
                                "OK",
                                "this is not json"
                        )
                );

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("banana")
                );

        assertEquals(
                "Nutrition API returned invalid JSON.",
                exception.getMessage()
        );
    }

    @Test
    void unsuccessfulResponseThrowsException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenReturn(
                        createResponse(
                                401,
                                "Unauthorized",
                                "{}"
                        )
                );

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("apple")
                );

        assertTrue(
                exception.getMessage().contains("401")
        );
    }

    @Test
    void ioExceptionBecomesNutritionApiException()
            throws IOException {

        final OkHttpClient client =
                mock(OkHttpClient.class);

        final Call call =
                mock(Call.class);

        when(client.newCall(any(Request.class)))
                .thenReturn(call);

        when(call.execute())
                .thenThrow(
                        new IOException("network error")
                );

        final ApiNinjasNutritionDataAccessObject dao =
                new ApiNinjasNutritionDataAccessObject(
                        client,
                        API_KEY
                );

        final NutritionApiException exception =
                assertThrows(
                        NutritionApiException.class,
                        () -> dao.search("apple")
                );

        assertEquals(
                "Unable to connect to the nutrition API.",
                exception.getMessage()
        );

        assertInstanceOf(
                IOException.class,
                exception.getCause()
        );
    }

    private static Response createResponse(
            int code,
            String message,
            String body) {

        final Request request =
                new Request.Builder()
                        .url("https://example.com")
                        .build();

        return new Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(code)
                .message(message)
                .body(
                        ResponseBody.create(
                                body,
                                MediaType.get(
                                        "application/json"
                                )
                        )
                )
                .build();
    }
}
